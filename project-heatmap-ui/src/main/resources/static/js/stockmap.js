let cachedData = null; // Global to store parsed nested data
let resizeTimeout;
const MIN_BOX_SIZE_FOR_TEXT = 25; // Minimum box size for showing symbol and change %
const SYMBOL_FONT_SIZE_FACTOR = 5; // Adjust to control symbol font size
const CHANGE_FONT_SIZE_FACTOR = 6; // Adjust to control change % font size
const EXCLUDED_INDUSTRIES = [
  "Commercial Services & Supplies",
  "Industrial Conglomerates",
  "Textiles, Apparel & Luxury Goods",
  "Metals & Mining",
  "Trading Companies & Distributors",
  "Construction",
  "Life Sciences Tools & Services",
  "Electrical Equipment",
  "Communications",
  "Distributors",
  "Packaging",
  "Logistics & Transportation",
  "Airlines",
  "Food Products",
  "Professional Services",
  "Building",
  "Tobacco",
  "Road & Rail",
  "Auto Components",
  "Leisure Products",
]; // Industries to exclude from displaying titles
const INDUSTRY_TITLE_PADDING = 20; // Padding for industry titles
const PLACEHOLDER_LOGO = "https://via.placeholder.com/30?text=Logo"; // Fallback logo

// Check for D3 dependency
if (typeof d3 === "undefined") {
  showError("D3 library not loaded");
  throw new Error("D3 library not loaded");
}

// Show loading state
function showLoading() {
  const container = document.getElementById("treemap-chart");
  if (container) {
    container.innerHTML = '<div class="loading">Loading...</div>';
  }
}

// Show error message
function showError(message) {
  const container = document.getElementById("treemap-chart");
  if (container) {
    container.innerHTML = ""; // Clear loading div
    container.innerHTML = `<div class="error">Error: ${message}</div>`;
  }
}

// Format market cap to readable string (e.g., $1.23T, $456B, $789M)
function formatMarketCap(cap) {
  if (cap >= 1_000_000) return (cap / 1_000_000).toFixed(2) + "T";
  if (cap >= 1_000) return (cap / 1_000).toFixed(2) + "B";
  return cap.toFixed(2) + "M";
}

// Process flat stock data into nested structure (industries -> stocks)
function processData(flatStocks) {
  console.log("Processing data:", flatStocks);
  const validStocks = flatStocks
    .map((s) => {
      if (!s.marketCap || s.marketCap <= 0) {
        console.warn(`Invalid or missing marketCap for stock ${s.symbol}`);
        return null;
      }
      if (!s.industry) {
        console.warn(`Missing industry for stock ${s.symbol}`);
        return null;
      }
      return {
        name: s.symbol,
        value: s.marketCap,
        change: s.marketPriceChgPct ?? 0,
        id: s.stockId,
        industry: s.industry,
        companyName: s.name || s.symbol, // Fallback to symbol
        logo: s.logo || PLACEHOLDER_LOGO, // Fallback to placeholder
      };
    })
    .filter(Boolean);

  console.log("Valid stocks:", validStocks);

  if (validStocks.length === 0) {
    showError("No valid stock data available");
    throw new Error("No valid stock data available");
  }

  return {
    name: "root",
    children: Array.from(
      d3.group(validStocks, (d) => d.industry),
      ([industry, stocks]) => ({
        name: industry,
        children: stocks.map((s) => ({
          name: s.name,
          value: s.value,
          change: s.change,
          id: s.id,
          companyName: s.companyName,
          logo: s.logo,
        })),
      })
    ),
  };
}

// Render D3 treemap
function renderD3Treemap(data) {
  console.log("Rendering treemap with data:", data);
  const container = document.getElementById("treemap-chart");
  if (!container) {
    console.error("❌ Container #treemap-chart not found");
    showError("Chart container not found");
    return;
  }

  // Clear the container (removes loading div)
  container.innerHTML = "";

  const width = window.innerWidth;
  const height = window.innerHeight;

  // Dynamic color scale for stock changes, aligned with Finviz S&P 500 Map
  const changes = data.children.flatMap((industry) =>
    industry.children.map((stock) => stock.change)
  );
  console.log("Price changes for color scale:", changes);
  const minChange = changes.length > 0 ? d3.min(changes) : -1;
  const maxChange = changes.length > 0 ? d3.max(changes) : 1;
  const colorScale = d3
    .scaleLinear()
    .domain([
      minChange,
      (minChange / 3) * 2,
      (minChange / 3) * 1,
      0,
      (maxChange / 3) * 1,
      (maxChange / 3) * 2,
      maxChange,
    ])
    .range([
      "rgb(245, 53, 56)", // -3% (bright red for large losses)
      "rgb(191, 64, 69)", // -2% (medium red)
      "rgb(139, 68, 78)", // -1% (darker red)
      "rgb(65, 69, 84)", // 0% (neutral gray)
      "rgb(53, 119, 77)", // +1% (light green)
      "rgb(47, 158, 78)", // +2% (medium green)
      "rgb(49, 204, 91)", // +3% (bright green for large gains)
    ])
    .interpolate(d3.interpolateRgb); // Smooth RGB interpolation

  // Reuse or create SVG
  let svg = d3.select("#treemap-chart").select("svg");
  if (svg.empty()) {
    svg = d3
      .select("#treemap-chart")
      .append("svg")
      .style("font-family", "Arial, Helvetica, sans-serif");
  }
  svg.attr("width", width).attr("height", height);

  // Clear previous SVG content
  svg.selectAll("*").remove();

  const root = d3
    .hierarchy(data)
    .sum((d) => d.value)
    .sort((a, b) => b.value - a.value);

  d3
    .treemap()
    .size([width, height])
    .paddingOuter(0)
    .paddingInner(2)
    .paddingTop(INDUSTRY_TITLE_PADDING)(root); // Extra padding for industry labels

  // Render industry groups
  const industryGroups = svg
    .selectAll("g.industry")
    .data(root.children)
    .join("g")
    .attr("class", "industry");

  industryGroups.each(function (industry) {
    const industryGroup = d3.select(this);

    // Add industry label only if not in EXCLUDED_INDUSTRIES (case-insensitive)
    if (
      !EXCLUDED_INDUSTRIES.some(
        (excluded) =>
          excluded.toLowerCase() === industry.data.name.toLowerCase()
      )
    ) {
      industryGroup
        .append("text")
        .attr("x", industry.x0 + 4)
        .attr("y", industry.y0 + INDUSTRY_TITLE_PADDING * 0.75)
        .text(industry.data.name)
        .attr("class", "industry")
        .attr("pointer-events", "none");
    }

    // Render stocks within each industry
    industryGroup
      .selectAll("g.stock")
      .data(industry.children)
      .join("g")
      .attr("class", "stock")
      .attr("transform", (d) => `translate(${d.x0},${d.y0})`)
      .on("click", (event, d) => {
        if (d.data.id) {
          window.location.href = `/ohlc?stockId=${d.data.id}`;
        }
      })
      .on("mouseover", function (event, d) {
        const tooltip = d3.select("#tooltip");
        tooltip.style("display", "block").html(`
                <div class="tooltip-header">
                  <img src="${d.data.logo}" />
                  <div class="company-name">${d.data.companyName}</div>
                </div>
                <div class="data-row">${d.data.change.toFixed(
                  2
                )}% | ${formatMarketCap(d.data.value)}</div>
              `);

        // Position tooltip near the mouse, with boundary checks
        const tooltipNode = tooltip.node();
        const tooltipRect = tooltipNode.getBoundingClientRect();
        let x = event.pageX + 10;
        let y = event.pageY + 10;

        // Prevent tooltip from going off-screen
        if (x + tooltipRect.width > window.innerWidth) {
          x = event.pageX - tooltipRect.width - 10;
        }
        if (y + tooltipRect.height > window.innerHeight) {
          y = event.pageY - tooltipRect.height - 10;
        }

        tooltip.style("left", `${x}px`).style("top", `${y}px`);
      })
      .on("mouseout", function () {
        d3.select("#tooltip").style("display", "none");
      })
      .each(function (d) {
        const stock = d3.select(this);
        const w = d.x1 - d.x0;
        const h = d.y1 - d.y0;

        // Skip stocks with invalid dimensions
        if (w <= 0 || h <= 0) {
          console.warn(
            `Skipping stock ${d.data.name} due to invalid dimensions: width=${w}, height=${h}`
          );
          return;
        }

        // Create clip-path to prevent text overflow
        stock
          .append("clipPath")
          .attr("id", `clip-${d.data.id}`)
          .append("rect")
          .attr("width", w)
          .attr("height", h);

        stock
          .append("rect")
          .attr("width", w)
          .attr("height", h)
          .attr("fill", colorScale(d.data.change))
          .attr("stroke", "#111")
          .attr("role", "button")
          .attr("tabindex", 0)
          .attr(
            "aria-label",
            `Stock ${d.data.name}, Change: ${d.data.change.toFixed(2)}%`
          );

        // Tooltip for all rectangles (fallback for accessibility)
        stock
          .append("title")
          .text(
            (d) =>
              `${d.data.companyName}: ${d.data.change.toFixed(
                2
              )}%, Market Cap: ${formatMarketCap(d.data.value)}`
          );

        // Only show text for boxes larger than MIN_BOX_SIZE_FOR_TEXT
        const minDimension = Math.min(w, h);
        if (minDimension >= MIN_BOX_SIZE_FOR_TEXT) {
          const fontSizeSymbol = Math.max(
            8,
            Math.min(14, minDimension / SYMBOL_FONT_SIZE_FACTOR)
          );
          const fontSizeChange = Math.max(
            6,
            Math.min(12, minDimension / CHANGE_FONT_SIZE_FACTOR)
          );
          const displayName =
            d.data.name.length > 10
              ? d.data.name.slice(0, 10) + "..."
              : d.data.name;

          // Add symbol text (centered, slightly adjusted upward for visual balance)
          stock
            .append("text")
            .attr("x", w / 2)
            .attr("y", h / 2 - fontSizeSymbol / 2)
            .attr("clip-path", `url(#clip-${d.data.id})`)
            .text(displayName)
            .style("font-size", `${fontSizeSymbol}px`)
            .style("font-weight", "bold")
            .style("fill", "white");

          // Add change percentage text (centered, below symbol)
          stock
            .append("text")
            .attr("x", w / 2)
            .attr("y", h / 2 + fontSizeSymbol / 2 + 2)
            .attr("clip-path", `url(#clip-${d.data.id})`)
            .text(`${d.data.change.toFixed(2)}%`)
            .style("font-size", `${fontSizeChange}px`)
            .style("fill", "white");
        }
      });
  });
}

// Debounced resize handler
function resizeHandler() {
  clearTimeout(resizeTimeout);
  resizeTimeout = setTimeout(() => {
    if (cachedData) {
      console.log("Resizing treemap");
      renderD3Treemap(cachedData);
    }
  }, 200);
}

// Initialize
document.addEventListener("DOMContentLoaded", () => {
  console.log("Initializing treemap");
  fetchData();
  window.addEventListener("resize", resizeHandler);
});

// Cleanup (optional, call if part of a component lifecycle)
function cleanup() {
  window.removeEventListener("resize", resizeHandler);
}

// Fetch and process data
function fetchData() {
  showLoading();
  console.log("Fetching data from /data/heatmap");
  fetch("/data/heatmap")
    .then((res) => {
      if (!res.ok)
        throw new Error(`Network response was not OK: ${res.status}`);
      return res.json();
    })
    .then((flatStocks) => {
      if (!Array.isArray(flatStocks)) {
        throw new Error("Invalid stock data: Expected an array");
      }
      const nestedData = processData(flatStocks);
      cachedData = nestedData;
      renderD3Treemap(nestedData);
    })
    .catch((err) => {
      console.error("❌ Failed to load stock data:", err.message);
      showError(`Failed to load stock data: ${err.message}`);
    });
}

let resizeTimeout;

// Format market cap or shares to readable string (e.g., $1.23T, 456M shares)
function formatNumber(value, isCurrency = false) {
  const prefix = isCurrency ? "$" : "";
  if (value >= 1_000_000) return `${prefix}${(value / 1_000_000).toFixed(2)}T`;
  if (value >= 1_000) return `${prefix}${(value / 1_000).toFixed(2)}B`;
  return `${prefix}${value.toFixed(2)}M`;
}

document.addEventListener("DOMContentLoaded", () => {
  const urlParams = new URLSearchParams(window.location.search);
  const stockId = urlParams.get("stockId");
  const companyName = urlParams.get("companyName");
  console.log("company name=" + companyName);

  if (!stockId) {
    console.error("❌ No stockId found in URL.");
    return;
  }

  console.log("Loaded stockId:", stockId);
  loadCandlestick(stockId, companyName);
  window.addEventListener("resize", resizeHandler);
});

// Debounced resize handler
function resizeHandler() {
  clearTimeout(resizeTimeout);
  resizeTimeout = setTimeout(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const stockId = urlParams.get("stockId");
    if (stockId) {
      console.log("Resizing candlestick chart");
      loadCandlestick(stockId);
    }
  }, 200);
}

// Cleanup
function cleanup() {
  window.removeEventListener("resize", resizeHandler);
}

function loadCandlestick(stockId, companyName) {
  fetch(`/data/ohlc?id=${stockId}`)
    .then((res) => {
      if (!res.ok)
        throw new Error(`Network response was not OK: ${res.status}`);
      return res.json();
    })
    .then((data) => {
      console.log("data=", data);

      // Assume data contains OHLCV array and metadata
      const ohlcvData = data.ohlcs;
      console.log("data.metadata=", data.metadata);
      if (!Array.isArray(ohlcvData) || ohlcvData.length === 0) {
        throw new Error("No OHLC data received");
      }

      // Render banner
      const banner = document.getElementById("banner");
      banner.innerHTML = `
            <img src="${data.logo}" />
            <span class="banner-item bold">${data.symbol}</span>
            <span class="banner-item">${data.companyName}</span>
            <span class="banner-item">Market Cap: ${formatNumber(
              data.marketCap || "N/A",
              true
            )}</span>
            <span class="banner-item">Industry: ${data.industry}</span>
            <span class="banner-item">Shares: ${formatNumber(
              data.shareOutstanding || "N/A"
            )}</span>
          `;

      // Filter to one year of data
      const today = new Date();
      const oneYearAgo = new Date();
      oneYearAgo.setFullYear(today.getFullYear() - 1);

      const filteredData = ohlcvData.filter(
        (d) => new Date(d.date) >= oneYearAgo
      );

      // Sort by date ascending
      filteredData.sort((a, b) => new Date(a.date) - new Date(b.date));

      const dates = filteredData.map((d) => d.date);

      const candlestickTrace = {
        type: "candlestick",
        name: "Price",
        x: dates,
        open: filteredData.map((d) => d.open),
        high: filteredData.map((d) => d.high),
        low: filteredData.map((d) => d.low),
        close: filteredData.map((d) => d.close),
        decreasing: {
          line: { color: "red" },
          fillcolor: "red",
        },
        increasing: {
          line: { color: "green" },
          fillcolor: "transparent",
        },
      };

      const volumeTrace = {
        x: dates,
        y: filteredData.map((d) => d.volume),
        type: "bar",
        xaxis: "x",
        yaxis: "y2",
        marker: {
          color: "rgba(128, 128, 255, 0.4)",
        },
        name: "Volume",
      };

      const layout = {
        paper_bgcolor: "#272931",
        plot_bgcolor: "#272931",
        font: { color: "white" },
        grid: { rows: 2, columns: 1, pattern: "independent" },
        xaxis: {
          type: "category",
          showgrid: false,
          tickformat: "%m",
          tickmode: "auto",
          nticks: 12,
          tickfont: { color: "white", size: 14 },
          rangeslider: { visible: false },
        },
        yaxis: {
          tickfont: { color: "white", size: 14 },
          domain: [0.3, 1],
          showgrid: true,
        },
        yaxis2: {
          domain: [0, 0.25],
          showgrid: true,
        },
        width: window.innerWidth,
        height: window.innerHeight - 60, // Adjust for banner
        margin: { t: 40, l: 60, r: 20, b: 60 },
      };

      Plotly.react(
        "candlestick-chart",
        [candlestickTrace, volumeTrace],
        layout,
        {
          responsive: true,
        }
      );
    })
    .catch((err) => {
      console.error("Error loading OHLC data:", err);
      const chart = document.getElementById("candlestick-chart");
      chart.innerHTML = `<div style="color: darkred; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);">Error: ${err.message}</div>`;
    });
}

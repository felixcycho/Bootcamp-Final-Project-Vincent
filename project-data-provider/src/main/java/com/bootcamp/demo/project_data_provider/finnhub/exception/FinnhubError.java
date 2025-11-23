package com.bootcamp.demo.project_data_provider.finnhub.exception;

import com.bootcamp.demo.project_data_provider.lib.Errorable;
import lombok.Getter;

@Getter
public enum FinnhubError implements Errorable {
  PROFILE2_EX(99, "Finnhub profile2 Webcall Error."), //
  JSON_PROCESSING_EX(98, "Finnhub profile2 Json Serialization Error."), //
  ;

  private int code;
  private String message;

  private FinnhubError(int code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public int getCode() {
    return this.code;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}

package com.opus.feature.pin.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PinInsertDTO {

	@NotBlank
	private String imagePath;

	private String prompt;

	private String negativePrompt;

	@NotBlank
	private String width;

	@NotBlank
	private String height;

	@NotBlank
	private String seed;
}

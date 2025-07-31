package gift.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoTextMessageTemplate(
        @JsonProperty("object_type") String objectType,
        @JsonProperty("text") String text,
        @JsonProperty("link") Link link,
        @JsonProperty("button_title") String buttonTitle
) {
    public record Link(
            @JsonProperty("web_url") String webUrl,
            @JsonProperty("mobile_web_url") String mobileWebUrl
    ) {}
}

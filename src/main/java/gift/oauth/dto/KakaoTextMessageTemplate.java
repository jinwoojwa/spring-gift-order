package gift.oauth.dto;

public record KakaoTextMessageTemplate(
        String objectType,
        String text,
        Link link,
        String buttonTitle
) {
    public record Link(
            String webUrl,
            String mobileWebUrl
    ) {}
}

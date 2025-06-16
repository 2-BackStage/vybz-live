package back.vybz.live_service.common.util;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CursorPage<T> {

    private List<T> content;
    private Boolean hasNext;
    private String nextCursor;

    @Builder
    private CursorPage(List<T> content, Boolean hasNext, String nextCursor) {
        this.content = content;
        this.hasNext = hasNext;
        this.nextCursor = nextCursor;
    }

    public <U> CursorPage<U> map(Function<? super T, ? extends U> mapper) {
        List<U> mappedContent = this.content.stream()
                .map(mapper)
                .collect(Collectors.toList());

        return CursorPage.<U>builder()
                .content(mappedContent)
                .hasNext(this.hasNext)
                .nextCursor(this.nextCursor)
                .build();
    }

    public static <T> CursorPage<T> of(List<T> content, int size, Function<T, String> getIdFunc) {
        boolean hasNext = content.size() > size;

        if (hasNext) {
            String nextCursor = getIdFunc.apply(content.get(size - 1)); // 마지막 요소 기준으로 커서 설정
            content = content.subList(0, size);

            return CursorPage.<T>builder()
                    .content(content)
                    .hasNext(true)
                    .nextCursor(nextCursor)
                    .build();
        }

        return CursorPage.<T>builder()
                .content(content)
                .hasNext(false)
                .nextCursor(null)
                .build();
    }

}

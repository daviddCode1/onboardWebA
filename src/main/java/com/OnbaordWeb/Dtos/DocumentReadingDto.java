package com.OnbaordWeb.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentReadingDto {

    private Long docId;

    private Integer isDocOneRead;

    private Integer isDocTwoRead;

    private Integer isDocThreeRead;

    private Integer isDocFourRead;

    private Integer isDocFiveRead;

    private Integer isDocSixRead;

    private Integer isDocSevenRead;

    private Long userId;
}

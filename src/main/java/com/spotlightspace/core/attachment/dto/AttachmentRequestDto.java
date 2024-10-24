package com.spotlightspace.core.attachment.dto;

import com.spotlightspace.common.entity.TableRole;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AttachmentRequestDto {
    private TableRole tableRole;
}

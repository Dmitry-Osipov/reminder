package com.osipov.reminder.domain.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Описание сущности ответа пользователя")
public class UserResponseDto extends AbstractUserDto {

    @Schema(description = "ID пользователя", example = "1")
    private Long id;

}

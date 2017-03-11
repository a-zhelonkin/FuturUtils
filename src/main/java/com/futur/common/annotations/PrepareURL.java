package com.futur.common.annotations;

import java.lang.annotation.*;

/**
 * Объект URL, помеченный этой аннотацией будет проверен на валидность
 */

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrepareURL {
}

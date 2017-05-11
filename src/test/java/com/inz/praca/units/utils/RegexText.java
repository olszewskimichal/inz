package com.inz.praca.units.utils;

import com.inz.praca.UnitTest;
import com.inz.praca.validators.EmailValidator;
import com.inz.praca.validators.PasswordValidator;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class RegexText {

    @Test
    public void checkPasswordRegex() throws Exception {
        String regex = PasswordValidator.PASS_REGEX_PATTERN;
        assertThat("zaq1@WSX".matches(regex)).isTrue();
        assertThat("zaqWS".matches(regex)).isFalse();
        assertThat("1111111111".matches(regex)).isFalse();
        assertThat("!!!!!!!!!!!".matches(regex)).isFalse();
        assertThat("        zaq1@WSX".matches(regex)).isFalse();
        assertThat("aaaaaaaaaaaa".matches(regex)).isFalse();
        assertThat("m@łpa1_\\".matches(regex)).isFalse();
        assertThat("Da✡mian1993".matches(regex)).isTrue();
        assertThat("\\u2721_\\bCoś_".matches(regex)).isTrue();
        assertThat(".*\\".matches(regex)).isFalse();
    }

    @Test
    public void checkEmailRegex() {
        String regex = EmailValidator.EMAIL_PATTERN;
        assertThat("zaq1@2.pl".matches(regex)).isTrue();
        assertThat("zaqWS".matches(regex)).isFalse();
        assertThat("".matches(regex)).isFalse();
        assertThat("   @o2.pl".matches(regex)).isFalse();
        assertThat("b   @o2.pl".matches(regex)).isFalse();
        assertThat("b    @    .pl".matches(regex)).isFalse();
    }
}

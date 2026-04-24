package com.example.library;

import com.example.library.Member.MemberTier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    private static Member standardMember() {
        return new Member("M001", "Alice Smith", "alice@example.com", new MemberTier.Standard());
    }

    @Test
    void record_accessors_are_generated() {
        var m = standardMember();
        assertThat(m.id()).isEqualTo("M001");
        assertThat(m.name()).isEqualTo("Alice Smith");
        assertThat(m.email()).isEqualTo("alice@example.com");
    }

    @Test
    void standard_borrow_limit_is_5() {
        assertThat(standardMember().tier().borrowLimit()).isEqualTo(5);
    }

    @Test
    void premium_borrow_limit_is_15() {
        var m = new Member("M002", "Bob", "bob@example.com", new MemberTier.Premium());
        assertThat(m.tier().borrowLimit()).isEqualTo(15);
    }

    @Test
    void staff_borrow_limit_is_max_int() {
        var m = new Member("M003", "Carol", "carol@example.com", new MemberTier.Staff("Engineering"));
        assertThat(m.tier().borrowLimit()).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    void tier_label_pattern_matching() {
        assertThat(new MemberTier.Standard().label()).isEqualTo("Standard");
        assertThat(new MemberTier.Premium().label()).isEqualTo("Premium");
        assertThat(new MemberTier.Staff("IT").label()).contains("IT");
    }

    @Test
    void compact_constructor_rejects_invalid_email() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Member("M1", "Name", "not-an-email", new MemberTier.Standard()));
    }

    @Test
    void compact_constructor_rejects_null_tier() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Member("M1", "Name", "n@example.com", null));
    }

    @Test
    void staff_tier_rejects_blank_department() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new MemberTier.Staff(""));
    }
}

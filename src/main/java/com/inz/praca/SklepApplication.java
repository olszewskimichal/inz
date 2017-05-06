package com.inz.praca;

import com.inz.praca.discount.DiscountService;
import com.inz.praca.discount.NewCustomerDiscountService;
import com.inz.praca.discount.NoDiscountService;
import com.inz.praca.discount.RegularCustomerDiscountService;
import com.inz.praca.registration.CurrentUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
@EnableJpaAuditing
public class SklepApplication {

    public static void main(String[] args) {
        SpringApplication.run(SklepApplication.class, args);
    }

    @Bean
    @Scope(value = "prototype")
    DiscountService discountService() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            CurrentUser user = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getUser().getOrders().isEmpty())
                return new NewCustomerDiscountService();
            if (user.getUser().getOrders().size() >= 3)
                return new RegularCustomerDiscountService();
        }
        return new NoDiscountService();
    }
}

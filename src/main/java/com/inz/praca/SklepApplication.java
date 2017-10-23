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
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@SpringBootApplication
public class SklepApplication {

    public static void main(String[] args) {
        SpringApplication.run(SklepApplication.class, args);
    }

    @Bean
    @Scope("prototype")
    DiscountService discountService() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).flatMap(v -> {
            CurrentUser user = (CurrentUser) v.getPrincipal();
            if (user.getUser().getOrders().isEmpty())
                return Optional.of(new NewCustomerDiscountService());
            if (user.getUser().getOrders().size() >= 3)
                return Optional.of(new RegularCustomerDiscountService());
            return Optional.of(new NoDiscountService());
        }).orElseGet(NoDiscountService::new);
    }
}


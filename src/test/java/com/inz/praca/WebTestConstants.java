package com.inz.praca;

public class WebTestConstants {

    public static class View {
        public static final String USERS = "users";
        public static final String ADD_PRODUCT = "newProduct";
        public static final String PRODUCT = "product";
        public static final String PRODUCTS = "products";
        public static final String EDIT_PRODUCT = "editProduct";
        public static final String HOME = "index";
        public static final String LOGIN = "login";
    }

    public static class ModelAttributeProperty {

        public static class USERS {
            public static final String EMAIL = "email";
            public static final String NAME = "name";
            public static final String LAST_NAME = "lastName";
            public static final String PAGE = "page";
        }

        public static class PRODUCT {
            public static final String NAME = "name";
            public static final String DESCRIPTION = "description";
            public static final String IMAGE_URL = "imageUrl";
            public static final String PRICE = "price";
            public static final String CATEGORY = "category";
            public static final String ID = "id";
        }

        public static class PRODUCTS {
            public static final String NAME = "name";
            public static final String PRICE = "price";
            public static final String PAGE = "page";
        }
    }

    public static class RedirectView {
        public static final String USERS = "redirect:/users";
        public static final String PRODUCTS = "redirect:/products";
    }

    public static class ValidationErrorCode {
        public static final String EMPTY_FIELD = "NotBlank";
        public static final String SIZE = "Size";
        public static final String VALID_PRICE = "ValidPrice";
    }

    public static class ErrorView {

        public static final String NOT_FOUND = "error/404";
    }

    public static class FlashMessageKey {
        public static final String FEEDBACK_MESSAGE = "feedbackMessage";
        public static final String CREATE_PRODUCT_CONFIRM = "createProductDone";
    }

    public class ModelAttributeName {

        public static final String USERS_LIST = "Users";
        public static final String SELECTED_PAGE_SIZE = "selectedPageSize";
        public static final String PRODUCT_LIST = "products";
        public static final String PAGER = "pager";
        public static final String PRODUCT_CREATE_FORM = "productCreateForm";
        public static final String PRODUCT = "product";
        public static final String PRODUCT_DTO = "productDTO";   //TODO uwspolnic
    }
}

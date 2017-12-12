package com.inz.praca.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Pager {

  private int buttonsToShow;

  private int startPage;

  private int endPage;

  public Pager(int totalPages, int currentPage, int buttonsToShow) {

    setButtonsToShow(buttonsToShow);

    int halfPagesToShow = this.buttonsToShow / 2;

    if (totalPages == 0) {
      startPage = 1;
      endPage = 1;
    } else if (totalPages <= this.buttonsToShow) {
      startPage = 1;
      endPage = totalPages;

    } else if ((currentPage - halfPagesToShow) <= 0) {
      startPage = 1;
      endPage = getButtonsToShow();

    } else if ((currentPage + halfPagesToShow) == totalPages) {
      startPage = currentPage - halfPagesToShow;
      endPage = totalPages;

    } else if (currentPage + halfPagesToShow > totalPages) {
      setStartPage(totalPages - getButtonsToShow() + 1);
      setEndPage(totalPages);

    } else {
      setStartPage(currentPage - halfPagesToShow);
      setEndPage(currentPage + halfPagesToShow);
    }

  }
}

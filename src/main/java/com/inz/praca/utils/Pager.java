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

		this.setButtonsToShow(buttonsToShow);

		int halfPagesToShow = this.buttonsToShow / 2;

		if (totalPages == 0) {
			this.startPage = 1;
			this.endPage = 1;
		}
		else if (totalPages <= this.buttonsToShow) {
			this.startPage = 1;
			this.endPage = totalPages;

		}
		else if ((currentPage - halfPagesToShow) <= 0) {
			this.startPage = 1;
			this.endPage = this.getButtonsToShow();

		}
		else if ((currentPage + halfPagesToShow) == totalPages) {
			this.startPage = currentPage - halfPagesToShow;
			this.endPage = totalPages;

		}
		else if (currentPage + halfPagesToShow > totalPages) {
			this.setStartPage(totalPages - this.getButtonsToShow() + 1);
			this.setEndPage(totalPages);

		}
		else {
			this.setStartPage(currentPage - halfPagesToShow);
			this.setEndPage(currentPage + halfPagesToShow);
		}

	}
}

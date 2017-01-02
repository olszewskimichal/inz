package com.inz.praca.units.utils;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.utils.Pager;
import org.junit.Test;

public class PagerTest {

	@Test
	public void testPager(){
		Pager pager = new Pager(10, 1, 5);
		assertThat(pager.getButtonsToShow()).isEqualTo(5);
		assertThat(pager.getStartPage()).isEqualTo(1);
		assertThat(pager.getEndPage()).isEqualTo(5);

		pager = new Pager(6, 6, 6);
		assertThat(pager.getButtonsToShow()).isEqualTo(6);
		assertThat(pager.getStartPage()).isEqualTo(1);
		assertThat(pager.getEndPage()).isEqualTo(6);

		pager = new Pager(4, 6, 6);
		assertThat(pager.getButtonsToShow()).isEqualTo(6);
		assertThat(pager.getStartPage()).isEqualTo(1);
		assertThat(pager.getEndPage()).isEqualTo(4);

		pager = new Pager(6, 6, 6);
		assertThat(pager.getButtonsToShow()).isEqualTo(6);
		assertThat(pager.getStartPage()).isEqualTo(1);
		assertThat(pager.getEndPage()).isEqualTo(6);

		pager = new Pager(18, 1, 6);
		assertThat(pager.getButtonsToShow()).isEqualTo(6);
		assertThat(pager.getStartPage()).isEqualTo(1);
		assertThat(pager.getEndPage()).isEqualTo(6);

		pager = new Pager(18, 3, 6);
		assertThat(pager.getButtonsToShow()).isEqualTo(6);
		assertThat(pager.getStartPage()).isEqualTo(1);
		assertThat(pager.getEndPage()).isEqualTo(6);

		pager = new Pager(18, 5, 6);
		assertThat(pager.getButtonsToShow()).isEqualTo(6);
		assertThat(pager.getStartPage()).isEqualTo(2);
		assertThat(pager.getEndPage()).isEqualTo(8);

		pager = new Pager(10, 5, 10);
		assertThat(pager.getButtonsToShow()).isEqualTo(10);
		assertThat(pager.getStartPage()).isEqualTo(1);
		assertThat(pager.getEndPage()).isEqualTo(10);

		pager = new Pager(10, 8, 10);
		assertThat(pager.getButtonsToShow()).isEqualTo(10);
		assertThat(pager.getStartPage()).isEqualTo(1);
		assertThat(pager.getEndPage()).isEqualTo(10);

		pager = new Pager(6, 8, 5);
		assertThat(pager.getButtonsToShow()).isEqualTo(5);
		assertThat(pager.getStartPage()).isEqualTo(2);
		assertThat(pager.getEndPage()).isEqualTo(6);

		pager = new Pager(10, 6, 9);
		assertThat(pager.getButtonsToShow()).isEqualTo(9);
		assertThat(pager.getStartPage()).isEqualTo(2);
		assertThat(pager.getEndPage()).isEqualTo(10);


		pager = new Pager(4, 3, 4);
		assertThat(pager.getButtonsToShow()).isEqualTo(4);
		assertThat(pager.getStartPage()).isEqualTo(1);
		assertThat(pager.getEndPage()).isEqualTo(4);
	}
}

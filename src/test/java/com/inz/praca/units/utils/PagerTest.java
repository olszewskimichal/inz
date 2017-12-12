package com.inz.praca.units.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.UnitTest;
import com.inz.praca.utils.Pager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category(UnitTest.class)
public class PagerTest {

  @Test
  public void testPager() {
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

  @Test
  public void listGroup() {
    //given
    ArrayList<String> lista = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));
    //when
    if (lista.size() % 2 != 0) {
      lista.add("");
    }
    List<String> result = IntStream.range(0, lista.size() / 2).map(i -> i * 2)
        .mapToObj(i -> lista.get(i) + lista.get(i + 1))
        .collect(Collectors.toList());
    //then
    assertThat(result.size()).isEqualTo(3);
    assertThat(result).containsExactly("ab", "cd", "e");
  }
}

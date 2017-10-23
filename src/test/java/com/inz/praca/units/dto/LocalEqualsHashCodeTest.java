package com.inz.praca.units.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Basically a copy of the EqualsHashCodeTestCase but with Junit 1.5 annotations
 * instead of extending TestCase.
 * <p>
 * This should be used whenever you are overriding .equals and .hashcode.
 *
 * @author graywatson
 */
public abstract class LocalEqualsHashCodeTest<T> {

    private static final int NUM_ITERATIONS = 100;
    private Object eq1;
    private Object eq2;
    private Object eq3;
    private Object neq;

    /**
     * Creates and returns an instance of the class under test.
     *
     * @return a new instance of the class under test; each object returned
     * from this method should compare equal to each other.
     * @throws Exception
     */
    protected abstract T createInstance();

    /**
     * Creates and returns an instance of the class under test.
     *
     * @return a new instance of the class under test; each object returned
     * from this method should compare equal to each other, but not to the
     * objects returned from {@link #createInstance() createInstance}.
     * @throws Exception
     */
    protected abstract T createNotEqualInstance();

    /**
     * Sets up the test fixture.
     *
     * @throws Exception
     */
    @Before
    public void setUp() {

        this.eq1 = this.createInstance();
        this.eq2 = this.createInstance();
        this.eq3 = this.createInstance();
        this.neq = this.createNotEqualInstance();

        // We want these assertions to yield errors, not failures.
        try {
            assertNotNull("createInstance() returned null", this.eq1);
            assertNotNull("2nd createInstance() returned null", this.eq2);
            assertNotNull("3rd createInstance() returned null", this.eq3);
            assertNotNull("createNotEqualInstance() returned null", this.neq);

            assertNotSame(this.eq1, this.eq2);
            assertNotSame(this.eq1, this.eq3);
            assertNotSame(this.eq1, this.neq);
            assertNotSame(this.eq2, this.eq3);
            assertNotSame(this.eq2, this.neq);
            assertNotSame(this.eq3, this.neq);

            assertEquals(
                    "1st and 2nd equal instances of different classes",
                    this.eq1.getClass(),
                    this.eq2.getClass());
            assertEquals(
                    "1st and 3rd equal instances of different classes",
                    this.eq1.getClass(),
                    this.eq3.getClass());
            assertEquals(
                    "1st equal instance and not-equal instance of different classes",
                    this.eq1.getClass(),
                    this.neq.getClass());
        } catch (AssertionError ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    /**
     * Tests whether <code>equals</code> holds up against a new
     * <code>Object</code> (should always be <code>false</code>).
     */
    @Test
    public final void testEqualsAgainstNewObject() {
        Object o = new Object();

        assertFalse("new Object() vs. 1st", this.eq1.equals(o));
        assertFalse("new Object() vs. 2nd", this.eq2.equals(o));
        assertFalse("new Object() vs. 3rd", this.eq3.equals(o));
        assertFalse("new Object() vs. not-equal", this.neq.equals(o));
    }

    /**
     * Tests whether <code>equals</code> holds up against <code>null</code>.
     */
    @Test
    public final void testEqualsAgainstNull() {
        assertFalse("null vs. 1st", this.eq1.equals(null));
        assertFalse("null vs. 2nd", this.eq2.equals(null));
        assertFalse("null vs. 3rd", this.eq3.equals(null));
        assertFalse("null vs. not-equal", this.neq.equals(null));
    }

    /**
     * Tests whether <code>equals</code> holds up against objects that should
     * not compare equal.
     */
    @Test
    public final void testEqualsAgainstUnequalObjects() {
        assertFalse("1st vs. not-equal", this.eq1.equals(this.neq));
        assertFalse("2nd vs. not-equal", this.eq2.equals(this.neq));
        assertFalse("3rd vs. not-equal", this.eq3.equals(this.neq));

        assertFalse("not-equal vs. 1st", this.neq.equals(this.eq1));
        assertFalse("not-equal vs. 2nd", this.neq.equals(this.eq2));
        assertFalse("not-equal vs. 3rd", this.neq.equals(this.eq3));
    }

    /**
     * Tests whether <code>equals</code> is <em>consistent</em>.
     */
    @Test
    public final void testEqualsIsConsistentAcrossInvocations() {
        for (int i = 0; i < LocalEqualsHashCodeTest.NUM_ITERATIONS; ++i) {
            this.testEqualsAgainstNewObject();
            this.testEqualsAgainstNull();
            this.testEqualsAgainstUnequalObjects();
            this.testEqualsIsReflexive();
            this.testEqualsIsSymmetricAndTransitive();
        }
    }

    /**
     * Tests whether <code>equals</code> is <em>reflexive</em>.
     */
    @Test
    public final void testEqualsIsReflexive() {
        assertEquals("1st equal instance", this.eq1, this.eq1);
        assertEquals("2nd equal instance", this.eq2, this.eq2);
        assertEquals("3rd equal instance", this.eq3, this.eq3);
        assertEquals("not-equal instance", this.neq, this.neq);
    }

    /**
     * Tests whether <code>equals</code> is <em>symmetric</em> and
     * <em>transitive</em>.
     */
    @Test
    public final void testEqualsIsSymmetricAndTransitive() {
        assertEquals("1st vs. 2nd", this.eq1, this.eq2);
        assertEquals("2nd vs. 1st", this.eq2, this.eq1);

        assertEquals("1st vs. 3rd", this.eq1, this.eq3);
        assertEquals("3rd vs. 1st", this.eq3, this.eq1);

        assertEquals("2nd vs. 3rd", this.eq2, this.eq3);
        assertEquals("3rd vs. 2nd", this.eq3, this.eq2);
    }

    /**
     * Tests the <code>hashCode</code> contract.
     */
    @Test
    public final void testHashCodeContract() {
        assertEquals("1st vs. 2nd", this.eq1.hashCode(), this.eq2.hashCode());
        assertEquals("1st vs. 3rd", this.eq1.hashCode(), this.eq3.hashCode());
        assertEquals("2nd vs. 3rd", this.eq2.hashCode(), this.eq3.hashCode());
    }

    /**
     * Tests the consistency of <code>hashCode</code>.
     */
    @Test
    public final void testHashCodeIsConsistentAcrossInvocations() {
        int eq1Hash = this.eq1.hashCode();
        int eq2Hash = this.eq2.hashCode();
        int eq3Hash = this.eq3.hashCode();
        int neqHash = this.neq.hashCode();

        for (int i = 0; i < LocalEqualsHashCodeTest.NUM_ITERATIONS; ++i) {
            assertEquals("1st equal instance", eq1Hash, this.eq1.hashCode());
            assertEquals("2nd equal instance", eq2Hash, this.eq2.hashCode());
            assertEquals("3rd equal instance", eq3Hash, this.eq3.hashCode());
            assertEquals("not-equal instance", neqHash, this.neq.hashCode());
        }
    }
}

package org.homs.gamba.stub.seqs;

import static org.homs.gamba.utils.Seq.rep;
import static org.homs.gamba.utils.Seq.seq;
import junit.framework.Assert;

import org.junit.Test;

public class SeqMain {

	@Test
	public void test1() {

		Assert.assertEquals("[1, 2, 3]", seq(1, 2, 3).toString());
		Assert.assertEquals("[1, 2, 3, 4]", seq(1, seq(2, 3), 4).toString());
		Assert.assertEquals("[2, 3, 2, 3, 2, 3]", rep(3, 2, 3).toString());
		Assert.assertEquals("[1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5]", rep(3, 1, seq(2, seq(3, 4), 5)).toString());
		Assert.assertEquals("[1, 2, 3, 2, 3, 2, 3, 4]", seq(1, rep(3, 2, 3), 4).toString());
	}

}

package org.homs.gamba.stub.seqs;

import static org.homs.gamba.utils.Seq.rep;
import static org.homs.gamba.utils.Seq.seq;
import static org.homs.gamba.utils.Seq.enList;
import junit.framework.Assert;

import org.junit.Test;

public class SeqTest {

	@Test
	public void test1() {

		Assert.assertEquals("[1, 2, 3]", enList(seq(1, 2, 3)).toString());
		Assert.assertEquals("[1, 2, 3, 4]", enList(seq(1, seq(2, 3), 4)).toString());
		Assert.assertEquals("[2, 3, 2, 3, 2, 3]", enList(rep(3, 2, 3)).toString());
		Assert.assertEquals("[1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5]", enList(rep(3, 1, seq(2, seq(3, 4), 5))).toString());
		Assert.assertEquals("[1, 2, 3, 2, 3, 2, 3, 4]", enList(seq(1, rep(3, 2, 3), 4)).toString());
	}

}

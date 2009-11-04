package org.homs.sokoban;

public class DummyMapHashImpl implements IMapHash {

    public DummyMapHashImpl() {
	//
    }

    /*
     * (non-Javadoc)
     *
     * @see org.homs.sokoban.IMapHash#cut(org.homs.sokoban.MapaGen, int)
     */
    public boolean cuttable(final MapaGen mapaGen, final int level) {
	return false;
    }
}

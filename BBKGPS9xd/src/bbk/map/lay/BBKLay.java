package bbk.map.lay;

import bbk.bbk.box.BBKSoft;
import bbk.map.abc.BBKMapImage;
import bbk.map.lay.BBKMapLay.Lay_type;
import bbk.map.lay.BBKMapLay.line_type;
import bbk.sys.abc.BBKSYS;

public class BBKLay {

	// ==========================================================================================
	// ==========================================================================================
	// ==========================================================================================
	public Lay_type laygps, layask, layfav, laymes, laytmp;

	public void LayInt() {
		// --------------------------------------------------------------------------
		laygps = new Lay_type();
		laygps.line.add(new line_type());
		// --------------------------------------------------------------------------
		layfav = new Lay_type();
		// --------------------------------------------------------------------------
		layask = new Lay_type();
		laymes = new Lay_type();
		laytmp = new Lay_type();
		// --------------------------------------------------------------------------
	}

	// ==========================================================================================

	public void LayShow(int mapzm) {
		// ----------------------------------------------------
		BBKMapLayShow.LayShow(layfav, BBKMapImage.Reds);
		BBKMapLayShow.LayShow(layask, BBKMapImage.Blue);
		BBKMapLayShow.LayShow(laygps, BBKMapImage.Reds);
		// ----------------------------------------------------
		BBKMapLayShow.LayShow(laymes, BBKMapImage.Blue);
		BBKMapLayShow.LayShow(laytmp, BBKMapImage.Blue);
		// ----------------------------------------------------
	}

	// =============================================================================
	public void LayLoad(Lay_type lay, String pathname) {
		BBKMapLay.layload(lay, pathname);
	}

	public void LaySave(Lay_type lay, String pathname) {
		BBKMapLay.laysave(lay, pathname);
	}

	// =============================================================================
	public void LayClears() {
		// ----------------------------------------------------
		layask.clear();
		laymes.clear();
		laytmp.clear();
		// ----------------------------------------------------
	}

	// =============================================================================
	// =============================================================================
	// =============================================================================
	// =============================================================================
	// =============================================================================
	public void LaysFavLoad() {
		LayLoad(layfav, BBKSoft.PathSets + "Fav");
	}

	public void LaysFavSave() {
		LaySave(layfav, BBKSoft.PathSets + "Fav");
	}

	public void LaysSave() {
		// ----------------------------------------------------
		LaysFavSave();
		// ----------------------------------------------------
		if (laygps.line.size() > 0) {
			if (laygps.line.get(0).p.size() > 1) {
				// --------------------------------------------
				LaySave(laygps, BBKSoft.PathBbjs + BBKSYS.bbtFileName);
				// --------------------------------------------
			}
		}
	}
	// =============================================================================
	// =============================================================================
	// =============================================================================
}

package br.org.scadabr.rt.dataSource.asciiFile;

import com.serotonin.mango.rt.dataImage.types.MangoValue;
import com.serotonin.mango.rt.dataSource.PointLocatorRT;

import br.org.scadabr.vo.dataSource.asciiFile.ASCIIFilePointLocatorVO;

public class ASCIIFilePointLocatorRT extends PointLocatorRT {
	private final ASCIIFilePointLocatorVO vo;
	private MangoValue currentValue;

	public ASCIIFilePointLocatorRT(ASCIIFilePointLocatorVO vo) {
		this.vo = vo;
	}

	@Override
	public boolean isSettable() {
		return vo.isSettable();
	}

	public ASCIIFilePointLocatorVO getVo() {
		return vo;
	}

	public void setCurrentValue(MangoValue currentValue) {
		this.currentValue = currentValue;
	}

}

package semestralka;

import java.util.Arrays;

public class Matice {
	
	private double[][] maticeSousednosti = new double[StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST+1][StaticData.POCET_HOSPOD+StaticData.POCET_PREKLADIST+1];
	
	public Matice(Cesta[] cestyPivovaru, Cesta[][] cestyPrekladist, Cesta[][] cestyHospod) {
		
		for(int i = 0; i < this.maticeSousednosti.length; i++) {
			Arrays.fill(this.maticeSousednosti[i], 0.0);
		}
		
		for(int i = 0; i < cestyHospod.length; i++) {
			for(int j = 0; j < cestyHospod[i].length; j++) {
				int indexFrom = cestyHospod[i][j].getIdFrom();
				int indexTo = cestyHospod[i][j].getIdTo();
				double vzdalenost = cestyHospod[i][j].getVzdalenost();
				
				this.maticeSousednosti[indexFrom][indexTo] = vzdalenost;
			}
		}
		
		for(int i = 0; i < cestyPrekladist.length; i++) {
			for(int j = 0; j < cestyPrekladist[i].length; j++) {
				int indexFrom = cestyPrekladist[i][j].getIdFrom() + StaticData.POCET_HOSPOD;
				int indexTo = cestyPrekladist[i][j].getIdTo();
				double vzdalenost = cestyPrekladist[i][j].getVzdalenost();
				
				this.maticeSousednosti[indexFrom][indexTo] = vzdalenost;
			}
		}
		
		for(int i = 0; i < cestyPivovaru.length; i++) {
			int indexFrom = cestyPivovaru[i].getIdFrom() + StaticData.POCET_HOSPOD + StaticData.POCET_PREKLADIST;
			int indexTo = cestyPivovaru[i].getIdTo();
			double vzdalenost = cestyPivovaru[i].getVzdalenost();
			
			this.maticeSousednosti[indexFrom][indexTo] = vzdalenost;
		}
	}
	
	public double[][] getMaticeSousednosti() {
		return this.maticeSousednosti;
	}
}

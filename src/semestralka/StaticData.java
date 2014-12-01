package semestralka;

/**
 * Tøída obsahující konstanty a další pomocná data.
 * @author Vlada47 & Shag0n
 *
 */
public class StaticData {
	
	/**
	 * Pocet prekladist v simulaci.
	 */
	public static final int POCET_PREKLADIST = 8;
	
	/**
	 * Pocet hospod v simulaci.
	 */
	public static final int POCET_HOSPOD = 4000;
	
	/**
	 * Pocet cest vedouci z pivovaru.
	 */
	public static final int POCET_CEST_PIVOVARU = 50;
	
	/**
	 * Pocet cest vedouci z prekladiste.
	 */
	public static final int POCET_CEST_PREKLADISTE = 50;
	
	/**
	 * Pocet cest vedoucich z hospody.
	 */
	public static final int POCET_CEST_HOSPODY = 15;
	
	/**
	 * Souradnice pivovaru na mape - osa x.
	 */
	public static final int SOURADNICE_PIVOVARU_X = 250;
	
	/**
	 * Souradnice pivovaru na mape - osa y.
	 */
	public static final int SOURADNICE_PIVOVARU_Y = 250;
	
	/**
	 * Rozmezi mezi dvema prekladisti - osa x.
	 */
	public static final int ROZMEZI_PREKLADISTE_X = 125;
	
	/**
	 * Rozmezi mezi dvema prekladisti - osa y.
	 */
	public static final int ROZMEZI_PREKLADISTE_Y = 63;
	
	/**
	 * Rozmezi mezi dvema hospodami - osa x.
	 */
	public static final int ROZMEZI_HOSPODY_X = 6;
	
	/**
	 * Rozmezi mezi dvema hospodami - osa y.
	 */
	public static final int ROZMEZI_HOSPODY_Y = 5;
	
	/**
	 * Maximalni pocet kamionu dostupnych pivovaru.
	 */
	public static final int POCET_KAMIONU = 120;
	
	/**
	 * Maximalni pocet cisteren dostupnych pivovaru.
	 */
	public static final int POCET_CISTEREN = 200;
	
	/**
	 * Maximalni pocet aut dostupnych kazdemu prekladisti.
	 */
	public static final int POCET_AUT = 250;
	
	/**
	 * Pomer 1:n mezi tankovymi a sudovymi hospodami.
	 */
	public static final int POMER_HOSPOD = 20;
	
	/**
	 * Pauza mezi kroky simulace v milisekundach.
	 */
	public static final int SIMULACE_MILIS = 1000; 
	
	/**
	 * Pocet dnu, po ktere bude simulace probihat.
	 */
	public static final int POCET_DNU = 7;
	
	/**
	 * Hodina, behem ktere ma chodit nejvice objednavek.
	 */
	public static final int HODINA_OBJEDNAVEK = 10;
	
	/**
	 * Konstanta pro rozptyl Gaussovy krivky pouzivane pro rozrazeni casu objednavek.
	 */
	public static final double GAUSS_ROZSAH = 1.6;
	
	/**
	 * Hodina, do ktere lze jeste provadet a vyrizovat objednavky.
	 */
	public static final int MAX_HODINA_OBJEDNANI = 16;
	
	/**
	 * Hodina, od ktere lze uz provadet a vyrizovat objednavky.
	 */
	public static final int MIN_HODINA_OBJEDNANI = 8;
	
	/**
	 * Podil jedne hodiny, kolik zabere prelozit jeden sud.
	 */
	public static final double SUD_CAS = 1.0 / 12.0;
	
	/**
	 * Podil jedne hodiny, kolik zabere precerpat jeden hl piva.
	 */
	public static final double HEKTOLITR_CAS = 1.0 / 30.0;
}

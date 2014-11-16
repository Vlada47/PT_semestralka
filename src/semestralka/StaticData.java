package semestralka;

/**
 * Tøída obsahující konstanty a další pomocná data.
 * @author Vlada47 & Shag0n
 *
 */
public class StaticData {
	public static final int POCET_PREKLADIST = 8;
	public static final int POCET_HOSPOD = 4000;
	public static final int POCET_CEST_PIVOVARU = 50;
	public static final int POCET_CEST_PREKLADISTE = 50;
	public static final int POCET_CEST_HOSPODY = 15;
	
	public static final int SOURADNICE_PIVOVARU_X = 250;
	public static final int SOURADNICE_PIVOVARU_Y = 250;
	
	public static final int ROZMEZI_PREKLADISTE_X = 125; //vzdalenost mezi prekladisti na ose x
	public static final int ROZMEZI_PREKLADISTE_Y = 63; //vzdalenost mezi prekladisti na ose y
	public static final int ROZMEZI_HOSPODY_X = 6; //vzdalenost mezi hospodami na ose x
	public static final int ROZMEZI_HOSPODY_Y = 5; // vzdalenost mezi hospodami na ose y
	
	//public static final double HODIN_NA_SUD = 1.0/12.0;
	//public static final double HODIN_NA_HEKTOLITR = 1.0/30.0;
	
	public static final int KAMIONY_K_VYSLANI = 15; //pocet kamionu, ktere se vyslou k jednomu prekladisti v urceny cas
	public static final int POCET_KAMIONU = KAMIONY_K_VYSLANI*POCET_PREKLADIST*2; //
	public static final int POCET_CISTEREN = 200; //pocet cisteren pivovaru
	public static final int POCET_AUT = 475; //pocet aut pro jedno prekladiste
	
	public static final int POMER_HOSPOD = 20; //kolikrat vic je sudovych hospod nez tankovych
	
	public static final int SIMULACE_MILIS = 1000; //doba cekani mezi kroky simulace 
	
	public static final int HODINA_OBJEDNAVEK = 10; //hodina, ve ktere chodi nejvice objednavek - vrchol Gaussovy krivky
	public static final double GAUSS_ROZSAH = 1.6; //parametr pro zmenu sirky Gaussovy krivky
	
	public static final int MAX_HODINA_OBJEDNANI = 16; //hodina, do ktere mohou byt vyrizovany objednavky
	public static final int MIN_HODINA_OBJEDNANI = 8; //hodina, od ktere mohou byt vyrizovany objednavky
}

package sk.beacode.beacodeapp.domain;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import sk.beacode.beacodeapp.domain.entity.PointEntity;

public class Trilateration {

	private static List<PointEntity> distances = new ArrayList<>();

	@DebugLog
	public static PointEntity getLocation(PointEntity p1, PointEntity p2, PointEntity p3,
	                                      double d1, double d2, double d3) {

//		p1.setX(0);
//		p1.setY(0);
//		p2.setX(100);
//		p2.setY(0);
//		p3.setX(100);
//		p3.setY(100);
//
//		d1 = 100;
//		d2 = 102.59;
//		d3 = 80.78;

		System.out.println("getLocation====================================");
		System.out.println(d1);
		System.out.println(d2);
		System.out.println(d3);
		//DECLARACAO DE VARIAVEIS
		PointEntity retorno = new PointEntity(0, 0);
		double[] P1   = new double[2];
		double[] P2   = new double[2];
		double[] P3   = new double[2];
		double[] ex   = new double[2];
		double[] ey   = new double[2];
		double[] p3p1 = new double[2];
		double jval  = 0;
		double temp  = 0;
		double ival  = 0;
		double p3p1i = 0;
		double triptx;
		double xval;
		double yval;
		double t1;
		double t2;
		double t3;
		double t;
		double exx;
		double d;
		double eyy;

		//TRANSFORMA OS PONTOS EM VETORES
		//PONTO 1
		P1[0] = p1.getX();
		P1[1] = p1.getY();
		//PONTO 2
		P2[0] = p2.getX();
		P2[1] = p2.getY();
		//PONTO 3
		P3[0] = p3.getX();
		P3[1] = p3.getY();

		// Ked sa X rovnaju
//		if (P1[1] == P2[1] && P1[1] == P3[1])  {
//			double totalD = d1 + d2;
//			double positionD = Math.abs(p2.getX() - p2.getX());
//
//			double x = (d1 / totalD) * positionD;
//
//			if (p1.getX() > p2.getX()) {
//				x = p1.getX() - x;
//			} else {
//				x = p1.getX() + x;
//			}
//
//			double y = p1.getY();
//
//			retorno.setX(x);
//			retorno.setY(y);
//			return retorno;
//		}
//
//		// Ked sa Y rovnaju
//		if (P1[0] == P2[0] && P1[0] == P3[0])  {
//			double totalD = d1 + d2;
//			double positionD = Math.abs(p2.getY() - p2.getY());
//
//			double y = (d1 / totalD) * positionD;
//
//			if (p1.getY() > p2.getY()) {
//				y = p1.getY() - y;
//			} else {
//				y = p1.getY() + y;
//			}
//
//			double x = p1.getX();
//
//			retorno.setX(x);
//			retorno.setY(y);
//			return retorno;
//		}

//		//TRANSFORMA O VALOR DE METROS PARA A UNIDADE DO MAPA
//		//DISTANCIA ENTRE O PONTO 1 E A MINHA LOCALIZACAO
//		d1 = (d1 / 100000);
//		//DISTANCIA ENTRE O PONTO 2 E A MINHA LOCALIZACAO
//		d2 = (d2 / 100000);
//		//DISTANCIA ENTRE O PONTO 3 E A MINHA LOCALIZACAO
//		d3 = (d3 / 100000);

		for (int i = 0; i < P1.length; i++) {
			t1   = P2[i];
			t2   = P1[i];
			t    = t1 - t2;
			temp += (t*t);
		}
		d = Math.sqrt(temp);
		for (int i = 0; i < P1.length; i++) {
			t1    = P2[i];
			t2    = P1[i];
			exx   = (t1 - t2)/(Math.sqrt(temp));
			ex[i] = exx;
		}
		for (int i = 0; i < P3.length; i++) {
			t1      = P3[i];
			t2      = P1[i];
			t3      = t1 - t2;
			p3p1[i] = t3;
		}
		for (int i = 0; i < ex.length; i++) {
			t1 = ex[i];
			t2 = p3p1[i];
			ival += (t1*t2);
		}
		for (int  i = 0; i < P3.length; i++) {
			t1 = P3[i];
			t2 = P1[i];
			t3 = ex[i] * ival;
			t  = t1 - t2 -t3;
			p3p1i += (t*t);
		}
		for (int i = 0; i < P3.length; i++) {
			t1 = P3[i];
			t2 = P1[i];
			t3 = ex[i] * ival;
			eyy = (t1 - t2 - t3)/Math.sqrt(p3p1i);
			ey[i] = eyy;
		}
		for (int i = 0; i < ey.length; i++) {
			t1 = ey[i];
			t2 = p3p1[i];
			jval += (t1*t2);
		}
		xval = (Math.pow(d1, 2) - Math.pow(d2, 2) + Math.pow(d, 2))/(2*d);
		yval = ((Math.pow(d1, 2) - Math.pow(d3, 2) + Math.pow(ival, 2) + Math.pow(jval, 2))/(2*jval)) - ((ival/jval)*xval);

		t1 = p1.getX();
		t2 = ex[0] * xval;
		t3 = ey[0] * yval;
		triptx = t1 + t2 + t3;
		retorno.setX(triptx);
		t1 = p1.getY();
		t2 = ex[1] * xval;
		t3 = ey[1] * yval;
		triptx = t1 + t2 + t3;
		retorno.setY(triptx);

		if (distances.size() >= 20) {
			distances.remove(0);
		}

		distances.add(retorno);

		double x = 0;
		double y = 0;
		for (PointEntity p : distances) {
			x += p.getX();
			y += p.getY();
		}
		x /= distances.size();
		y /= distances.size();

		return new PointEntity(x, y);
	}
}

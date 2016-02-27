package kr.co.makeit.hanium.list;

import android.location.Location;

public class Distance {

	public double cal(double mylat, double mylon, double positionLat, double positionLon){
		Location locationA = new Location("locA");
		locationA.setLatitude(mylat);
		locationA.setLongitude(mylon);
		
		Location locationB = new Location("locB");
		locationB.setLatitude(positionLat);
		locationB.setLongitude(positionLon);
		return locationA.distanceTo(locationB);
		
		
	}
}

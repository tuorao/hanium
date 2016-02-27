package kr.co.makeit.hanium.domain;

import android.os.Parcel;
import android.os.Parcelable;



public class Facility implements Parcelable{
		/**
	 * 
	 */
		private int facSrl; // 시설번호
		private String facName; // 시설이름
		private String fadd; //시설 앞주소 ex) 서울시 송파구 문정동
		private String ladd; // 시설 나머지 주소 ex) 66-4
		private double lat; //시설 위도
		private double lon; //시설 경도
		private String facintro;
		private String phone; // 시설 번호
		private int faccate; // 시설 카테고리 0~ int타입으로
		private int facdel; // 삭제여부 0- 삭제안함 1- 삭제 
		private int memSrl; // 시설을 등록한 맴버 번호
		
		public Facility(){}
		
		public Facility(Parcel source) {
			// TODO Auto-generated constructor stub
			readFromParcel(source);
		}
		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			// TODO Auto-generated method stub
			dest.writeInt(facSrl);
			dest.writeString(facName);
			dest.writeString(fadd);
			dest.writeString(ladd);
			dest.writeDouble(lat);
			dest.writeDouble(lon);
			dest.writeString(facintro);
			dest.writeString(phone);
			dest.writeInt(faccate);
			dest.writeInt(facdel);
			dest.writeInt(memSrl);
			
		}
		
		private void readFromParcel(Parcel in){
			facSrl = in.readInt();
			facName = in.readString();
			fadd = in.readString();
			ladd = in.readString();
			lat = in.readDouble();
			lon = in.readDouble();
			facintro = in.readString();
			phone = in.readString();
			faccate = in.readInt();
			facdel = in.readInt();
			memSrl = in.readInt();
		}
		
		@SuppressWarnings("rawtypes")
		public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Facility>() {

			@Override
			public Facility createFromParcel(Parcel source) {
				// TODO Auto-generated method stub
				return new Facility(source);
			}

			@Override
			public Facility[] newArray(int size) {
				// TODO Auto-generated method stub
				return new Facility[size];
			}
		};
		
		public int getFacSrl() {
			return facSrl;
		}
		public void setFacSrl(int facSrl) {
			this.facSrl = facSrl;
		}
		public String getFacName() {
			return facName;
		}
		public void setFacName(String facName) {
			this.facName = facName;
		}
		public String getFadd() {
			return fadd;
		}
		public void setFadd(String fadd) {
			this.fadd = fadd;
		}
		public String getLadd() {
			return ladd;
		}
		public void setLadd(String ladd) {
			this.ladd = ladd;
		}
		public double getLat() {
			return lat;
		}
		public void setLat(double lat) {
			this.lat = lat;
		}
		public double getLon() {
			return lon;
		}
		public void setLon(double lon) {
			this.lon = lon;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public int getFaccate() {
			return faccate;
		}
		public void setFaccate(int faccate) {
			this.faccate = faccate;
		}
		public int getFacdel() {
			return facdel;
		}
		public void setFacdel(int facdel) {
			this.facdel = facdel;
		}
		public int getMemSrl() {
			return memSrl;
		}
		public void setMemSrl(int memSrl) {
			this.memSrl = memSrl;
		}
		public String getFacintro() {
			return facintro;
		}
		public void setFacintro(String facintro) {
			this.facintro = facintro;
		}

		
}

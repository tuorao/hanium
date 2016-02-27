package kr.co.makeit.hanium.group;

import kr.co.makeit.hanium.main.R;

public class GroupList {
	 
    String icon;
    int icon_R;
    int num;
    
    public GroupList(){}
  
    public GroupList(String getIcon){
    	icon = getIcon;
    	if(icon.equals("스포츠,레져")){
    		icon_R = R.drawable.a1;
    	} else if(icon.equals("음악,악기")){
    		icon_R = R.drawable.a2;
    	} else if(icon.equals("등산")){
    		icon_R = R.drawable.a3;
    	} else if(icon.equals("스터디,자기계발")){
    		icon_R = R.drawable.a4;
    	} else if(icon.equals("자전거")){
    		icon_R = R.drawable.a5;
    	} else if(icon.equals("자동차")){
    		icon_R = R.drawable.a6;
    	} else if(icon.equals("패션")){
    		icon_R = R.drawable.a7;
    	} else if(icon.equals("책,독서")){
    		icon_R = R.drawable.a8;
    	} else if(icon.equals("봉사활동")){
    		icon_R = R.drawable.a9;
    	} else if(icon.equals("사진")){
    		icon_R = R.drawable.a10;
    	} else if(icon.equals("여행,캠핑")){
    		icon_R = R.drawable.a11;
    	} else if(icon.equals("영화")){
    		icon_R = R.drawable.a12;
    	} else if(icon.equals("스키,보드")){
    		icon_R = R.drawable.a13;
    	} else if(icon.equals("낚시")){
    		icon_R = R.drawable.a14;
    	} else if(icon.equals("여성")){
    		icon_R = R.drawable.a15;
    	}
    }
    
    public void setNum(String Category){
    	if(Category.equals("선택")){
    		num = 0;
    	}else if(Category.equals("스포츠,레져")){
    		num = 1;
    	} else if(Category.equals("음악,악기")){
    		num = 2;
    	} else if(Category.equals("등산")){
    		num = 3;
    	} else if(Category.equals("스터디,자기계발")){
    		num = 4;
    	} else if(Category.equals("자전거")){
    		num = 5;
    	} else if(Category.equals("자동차")){
    		num = 6;
    	} else if(Category.equals("패션")){
    		num = 7;
    	} else if(Category.equals("책,독서")){
    		num = 8;
    	} else if(Category.equals("봉사활동")){
    		num = 9;
    	} else if(Category.equals("사진")){
    		num = 10;
    	} else if(Category.equals("여행,캠핑")){
    		num = 11;
    	} else if(Category.equals("영화")){
    		num = 12;
    	} else if(Category.equals("스키,보드")){
    		num = 13;
    	} else if(Category.equals("낚시")){
    		num = 14;
    	} else if(Category.equals("여성")){
    		num = 15;
    	}
    }
    
    public int getNum(){
    	return num;
    }
    
    public void setIcon(int a){
    	switch(a){
    	case 1:
    		icon_R = R.drawable.a1;
    		break;
    	case 2:
    		icon_R = R.drawable.a2;
    		break;
    	case 3:
    		icon_R = R.drawable.a3;
    		break;
    	case 4:
    		icon_R = R.drawable.a4;
    		break;
    	case 5:
    		icon_R = R.drawable.a5;
    		break;
    	case 6:
    		icon_R = R.drawable.a6;
    		break;
    	case 7:
    		icon_R = R.drawable.a7;
    		break;
    	case 8:
    		icon_R = R.drawable.a8;
    		break;
    	case 9:
    		icon_R = R.drawable.a9;
    		break;
    	case 10:
    		icon_R = R.drawable.a10;
    		break;
    	case 11:
    		icon_R = R.drawable.a11;
    		break;
    	case 12:
    		icon_R = R.drawable.a12;
    		break;
    	case 13:
    		icon_R = R.drawable.a13;
    		break;
    	case 14:
    		icon_R = R.drawable.a14;
    		break;
    	case 15:
    		icon_R = R.drawable.a15;
    		break;
    	}
    }
    
    public int getIcon(){
        return icon_R;
    }
        
}
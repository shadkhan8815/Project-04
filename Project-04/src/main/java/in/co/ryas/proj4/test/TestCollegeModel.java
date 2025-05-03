package in.co.ryas.proj4.test;

import in.co.ryas.proj4.model.CollegeModel;

public class TestCollegeModel {

	public static void main(String[] args) throws Exception {

		testnextPK();

	}

	public static void testnextPK() throws Exception {

		CollegeModel model = new CollegeModel();

		int i = model.nextPK();

		System.out.println("next pk is : " + i);
	}

}

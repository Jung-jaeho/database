import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

public class AppMain extends JFrame implements ActionListener {
	ArrayList<String> list1, list2, list3, list4, list5, list6, list7; //각 기본키 값들의 임시 저장소
	JTabbedPane mJTabbedPane;
	JPanel cusInfo, carCompInfo, servCenterInfo, campCarInfo, rentList, servRequest, serviceList, SearchPane, topTable,
			viewTable;
	DefaultTableModel cusTableM, carCompTableM, servCentTableM, campCarTableM, rentTableM, servReqTableM,
			servListTableM, SearchTableM;
	JTable cusTable, carCompTable, servCentTable, campCarTable, rentTable, servReqTable, servListTable, SearchTable;
	JButton btnSearch, btnRefresh, btnAdd, btnDelete, btnQUpdate, btnInitDB;

	InsertDialog insertDlg; //데이터 삽입 다이얼로그

	boolean isAdmin = true; //관리자 모드인지 아닌지

	DatabaseConnect mDBConn; //DB

	AppMain() {
		mDBConn = new DatabaseConnect();

		setTitle("마당 캠핑카");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		Init();

		add(topTable, BorderLayout.NORTH);
		add(mJTabbedPane, BorderLayout.CENTER);

		setSize(1024, 640);
		setVisible(true);
	}

	public static void main(String args[]) {
		new AppMain();
	}

	public void Init() {
		// 탭 패널 생성하고 각 패널 및 컴포넌트들 붙이기
		mJTabbedPane = new JTabbedPane();
		cusInfo = new JPanel();
		carCompInfo = new JPanel();
		servCenterInfo = new JPanel();
		campCarInfo = new JPanel();
		rentList = new JPanel();
		servRequest = new JPanel();
		serviceList = new JPanel();
		btnSearch = new JButton();
		btnRefresh = new JButton();
		btnQUpdate = new JButton();
		btnAdd = new JButton();
		btnDelete = new JButton();
		btnInitDB = new JButton();
		topTable = new JPanel();
		viewTable = new JPanel();

		topTable.setLayout(new FlowLayout(FlowLayout.RIGHT));
		viewTable.setLayout(new FlowLayout());

		btnInitDB.setText("DB 초기화"); //초기화 버튼
		btnSearch.setText("검색"); //검색 버튼
		btnRefresh.setText("조회"); //새로고침(조회 버튼)
		btnAdd.setText("추가"); //데이터 삽입 버튼
		btnQUpdate.setText("업데이트"); //데이터 변경 버튼
		btnDelete.setText("삭제"); //데이터 삭제 버튼

		mJTabbedPane.addTab("고객 정보", cusInfo);
		mJTabbedPane.addTab("캠핑카 회사 정보", carCompInfo);
		mJTabbedPane.addTab("정비소 정보", servCenterInfo);
		mJTabbedPane.addTab("캠핑카 정보", campCarInfo);
		mJTabbedPane.addTab("대여 목록", rentList);
		mJTabbedPane.addTab("정비 요청 목록", servRequest);
		mJTabbedPane.addTab("정비 정보", serviceList);

		topTable.add(btnInitDB);
		topTable.add(btnSearch);
		topTable.add(btnRefresh);
		topTable.add(btnAdd);
		topTable.add(btnQUpdate);
		topTable.add(btnDelete);

		btnInitDB.addActionListener(this);
		btnSearch.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnAdd.addActionListener(this);
		btnQUpdate.addActionListener(this);
		btnDelete.addActionListener(this);

/*		createCusInfo();
		createCarCompInfo();
		createServCenterInfo();
		createCampCarInfo();
		createRentList();
		createServRequest();
		createServiceList();
*/
		mJTabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) { //사용자가 검색 탭을 선택하면 새로고침, 삽입, 변경, 삭제 기능 비활성화
				// TODO Auto-generated method stub
				if (mJTabbedPane.getSelectedIndex() == 7) {
					btnRefresh.setEnabled(false);
					btnAdd.setEnabled(false);
					btnQUpdate.setEnabled(false);
					btnDelete.setEnabled(false);
				} else {
					btnRefresh.setEnabled(true);
					btnAdd.setEnabled(true);
					btnQUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
				}
			}

		});
	}

	public void createCusInfo() {
		// 고객 정보 패널 생성
		cusInfo.setBackground(new Color(225, 230, 246));
		cusInfo.setLayout(new BorderLayout());
		String condition = "";
		// condition = "where CUSADDR like \'%Gunsan%\'";

		cusTable = new JTable();
		cusTableM = new DefaultTableModel();

		list1 = new ArrayList<>();

		ArrayList<DescribeTableVO> arrDescribeTableVO = mDBConn.getTableDescription("Customer");
		Object[] columnsName = new Object[arrDescribeTableVO.size()];
		for (int i = 0; i < arrDescribeTableVO.size(); i++) {
			columnsName[i] = arrDescribeTableVO.get(i).getColumn_name();
		}
		cusTableM.setColumnIdentifiers(columnsName);

		Object[] rowData = new Object[arrDescribeTableVO.size()];
		System.out.println(arrDescribeTableVO.size());
		ArrayList<CustomerInfo> arrCustomerVO = mDBConn.selectCustomer(condition);
		for (int i = 0; i < arrCustomerVO.size(); i++) {
			System.out.println(arrCustomerVO.get(i).getLicNum());
			rowData[0] = arrCustomerVO.get(i).getLicNum();
			list1.add(arrCustomerVO.get(i).getLicNum());
			rowData[1] = arrCustomerVO.get(i).getCusName();
			rowData[2] = arrCustomerVO.get(i).getCusAddress();
			rowData[3] = arrCustomerVO.get(i).getCusPhoneNo();
			rowData[4] = arrCustomerVO.get(i).getCusEmail();
			rowData[5] = arrCustomerVO.get(i).getLastUseDate();
			rowData[6] = arrCustomerVO.get(i).getLastCarID();

			cusTableM.addRow(rowData);
		}

		cusTable.addMouseListener(new JTableMouseListener());
		cusTable.setModel(cusTableM);

		JScrollPane jsp = new JScrollPane(cusTable);
		cusInfo.add(jsp);
	}

	public void createCarCompInfo() {
		// 캠핑카 대여회사 정보 패널 생성
		carCompInfo.setBackground(new Color(225, 230, 246));
		carCompInfo.setLayout(new BorderLayout());
		String condition = "";
		// condition = "where CUSADDR like \'%Gunsan%\'";

		carCompTable = new JTable();
		carCompTableM = new DefaultTableModel();

		list2 = new ArrayList<>();

		ArrayList<DescribeTableVO> arrDescribeTableVO = mDBConn.getTableDescription("RentalCompany");
		Object[] columnsName = new Object[arrDescribeTableVO.size()];
		for (int i = 0; i < arrDescribeTableVO.size(); i++) {
			columnsName[i] = arrDescribeTableVO.get(i).getColumn_name();
		}

		carCompTableM.setColumnIdentifiers(columnsName);

		Object[] rowData = new Object[arrDescribeTableVO.size()];
		System.out.println(arrDescribeTableVO.size());
		ArrayList<RentalCompany> arrRentalCompanyVO = mDBConn.selectRentalCompany(condition);
		for (int i = 0; i < arrRentalCompanyVO.size(); i++) {
			System.out.println(arrRentalCompanyVO.get(i).getCompID());
			if (isAdmin)
				rowData[0] = arrRentalCompanyVO.get(i).getCompID();
			else
				rowData[0] = "INVISIBLE";
			list2.add(arrRentalCompanyVO.get(i).getCompID());
			rowData[1] = arrRentalCompanyVO.get(i).getCompName();
			rowData[2] = arrRentalCompanyVO.get(i).getCompAddress();
			rowData[3] = arrRentalCompanyVO.get(i).getCompPhoneNo();
			rowData[4] = arrRentalCompanyVO.get(i).getCompManager();
			rowData[5] = arrRentalCompanyVO.get(i).getCompManagerEmail();

			carCompTableM.addRow(rowData);
		}

		carCompTable.addMouseListener(new JTableMouseListener());
		carCompTable.setModel(carCompTableM);

		JScrollPane jsp = new JScrollPane(carCompTable);
		carCompInfo.add(jsp);
	}

	public void createServCenterInfo() {
		// 정비소 정보 패널 생성
		servCenterInfo.setBackground(new Color(225, 230, 246));
		servCenterInfo.setLayout(new BorderLayout());
		String condition = "";
		// condition = "where CUSADDR like \'%Gunsan%\'";

		servCentTable = new JTable();
		servCentTableM = new DefaultTableModel();

		list3 = new ArrayList<>();

		ArrayList<DescribeTableVO> arrDescribeTableVO = mDBConn.getTableDescription("CarServiceCenter");
		Object[] columnsName = new Object[arrDescribeTableVO.size()];
		for (int i = 0; i < arrDescribeTableVO.size(); i++) {
			columnsName[i] = arrDescribeTableVO.get(i).getColumn_name();
		}
		servCentTableM.setColumnIdentifiers(columnsName);

		Object[] rowData = new Object[arrDescribeTableVO.size()];
		System.out.println(arrDescribeTableVO.size());
		ArrayList<CarServiceCenter> arrServiceCenterVO = mDBConn.selectServiceCenter(condition);
		for (int i = 0; i < arrServiceCenterVO.size(); i++) {
			System.out.println(arrServiceCenterVO.get(i).getCenterID());
			if (isAdmin)
				rowData[0] = arrServiceCenterVO.get(i).getCenterID();
			else
				rowData[0] = "INVISIBLE";
			list3.add(arrServiceCenterVO.get(i).getCenterID());
			rowData[1] = arrServiceCenterVO.get(i).getCenterName();
			rowData[2] = arrServiceCenterVO.get(i).getCenterAddress();
			rowData[3] = arrServiceCenterVO.get(i).getCenterPhoneNo();
			rowData[4] = arrServiceCenterVO.get(i).getCenterManager();
			rowData[5] = arrServiceCenterVO.get(i).getCenterManagerEmail();

			servCentTableM.addRow(rowData);
		}

		servCentTable.addMouseListener(new JTableMouseListener());
		servCentTable.setModel(servCentTableM);

		JScrollPane jsp = new JScrollPane(servCentTable);
		servCenterInfo.add(jsp);
	}

	public void createCampCarInfo() {
		// 캠핑카 목록 패널 생성
		campCarInfo.setBackground(new Color(225, 230, 246));
		campCarInfo.setLayout(new BorderLayout());
		String condition = "";
		// condition = "where CUSADDR like \'%Gunsan%\'";

		campCarTable = new JTable();
		campCarTableM = new DefaultTableModel();

		list4 = new ArrayList<>();

		ArrayList<DescribeTableVO> arrDescribeTableVO = mDBConn.getTableDescription("CAMPINGCAR");
		Object[] columnsName = new Object[arrDescribeTableVO.size()];
		for (int i = 0; i < arrDescribeTableVO.size(); i++) {
			columnsName[i] = arrDescribeTableVO.get(i).getColumn_name();
		}
		campCarTableM.setColumnIdentifiers(columnsName);

		Object[] rowData = new Object[arrDescribeTableVO.size()];
		System.out.println(arrDescribeTableVO.size());
		ArrayList<CampingCarList> arrCampingCarListVO = mDBConn.selectCampingCarList(condition);
		for (int i = 0; i < arrCampingCarListVO.size(); i++) {
			System.out.println(arrCampingCarListVO.get(i).getCarID());
			if (isAdmin)
				rowData[0] = arrCampingCarListVO.get(i).getCarID();
			else
				rowData[0] = "INVISIBLE";
			list4.add(arrCampingCarListVO.get(i).getCarID());
			rowData[1] = arrCampingCarListVO.get(i).getCompanyID();
			rowData[2] = arrCampingCarListVO.get(i).getCarName();
			rowData[3] = arrCampingCarListVO.get(i).getCarSNum();
			rowData[4] = arrCampingCarListVO.get(i).getNoPassengers();
			rowData[5] = arrCampingCarListVO.get(i).getCarDetails1();
			rowData[6] = arrCampingCarListVO.get(i).getCarDetails2();
			rowData[7] = arrCampingCarListVO.get(i).getRentalFee();
			rowData[8] = arrCampingCarListVO.get(i).getRegDate();

			campCarTableM.addRow(rowData);
		}

		campCarTable.addMouseListener(new JTableMouseListener());
		campCarTable.setModel(campCarTableM);

		JScrollPane jsp = new JScrollPane(campCarTable);
		campCarInfo.add(jsp);
	}

	public void createRentList() {
		// 대여 목록 패널 생성
		rentList.setBackground(new Color(225, 230, 246));
		rentList.setLayout(new BorderLayout());
		String condition = "";
		// condition = "where CUSADDR like \'%Gunsan%\'";

		rentTable = new JTable();
		rentTableM = new DefaultTableModel();

		list5 = new ArrayList<>();

		ArrayList<DescribeTableVO> arrDescribeTableVO = mDBConn.getTableDescription("RentList");
		Object[] columnsName = new Object[arrDescribeTableVO.size()];
		for (int i = 0; i < arrDescribeTableVO.size(); i++) {
			columnsName[i] = arrDescribeTableVO.get(i).getColumn_name();
		}
		rentTableM.setColumnIdentifiers(columnsName);

		Object[] rowData = new Object[arrDescribeTableVO.size()];
		System.out.println(arrDescribeTableVO.size());
		ArrayList<RentList> arrRentListVO = mDBConn.selectRentList(condition);
		for (int i = 0; i < arrRentListVO.size(); i++) {
			System.out.println(arrRentListVO.get(i).getRentalID());
			if (isAdmin)
				rowData[0] = arrRentListVO.get(i).getRentalID();
			else
				rowData[0] = "INVISIBLE";
			list5.add(arrRentListVO.get(i).getRentalID());
			rowData[1] = arrRentListVO.get(i).getCampCarID();
			rowData[2] = arrRentListVO.get(i).getCusLicNum();
			rowData[3] = arrRentListVO.get(i).getRentCompID();
			rowData[4] = arrRentListVO.get(i).getStartDate();
			rowData[5] = arrRentListVO.get(i).getRentalPeriod();
			rowData[6] = arrRentListVO.get(i).getRentalBill();
			rowData[7] = arrRentListVO.get(i).getBillDate();
			rowData[8] = arrRentListVO.get(i).getOthers();
			rowData[9] = arrRentListVO.get(i).getExtBill();

			rentTableM.addRow(rowData);
		}

		rentTable.addMouseListener(new JTableMouseListener());
		rentTable.setModel(rentTableM);

		JScrollPane jsp = new JScrollPane(rentTable);
		rentList.add(jsp);
	}

	public void createServRequest() {
		// 정비 요청 목록 패널 생성
		servRequest.setBackground(new Color(225, 230, 246));
		servRequest.setLayout(new BorderLayout());
		String condition = "";
		// condition = "where CUSADDR like \'%Gunsan%\'";

		servReqTable = new JTable();
		servReqTableM = new DefaultTableModel();

		list6 = new ArrayList<>();

		ArrayList<DescribeTableVO> arrDescribeTableVO = mDBConn.getTableDescription("RequireServiceList");
		Object[] columnsName = new Object[arrDescribeTableVO.size()];
		for (int i = 0; i < arrDescribeTableVO.size(); i++) {
			columnsName[i] = arrDescribeTableVO.get(i).getColumn_name();
		}
		servReqTableM.setColumnIdentifiers(columnsName);

		Object[] rowData = new Object[arrDescribeTableVO.size()];
		System.out.println(arrDescribeTableVO.size());
		ArrayList<RequestServiceList> arrRequestServiceListVO = mDBConn.selectRequestServiceList(condition);
		for (int i = 0; i < arrRequestServiceListVO.size(); i++) {
			System.out.println(arrRequestServiceListVO.get(i).getReceiptID());
			if (isAdmin)
				rowData[0] = arrRequestServiceListVO.get(i).getReceiptID();
			else
				rowData[0] = "INVISIBLE";
			list6.add(arrRequestServiceListVO.get(i).getReceiptID());
			rowData[1] = arrRequestServiceListVO.get(i).getCusLicNum();
			rowData[2] = arrRequestServiceListVO.get(i).getRentalID();
			rowData[3] = arrRequestServiceListVO.get(i).getDate();
			rowData[4] = arrRequestServiceListVO.get(i).getCusPhoneNo();

			servReqTableM.addRow(rowData);
		}

		servReqTable.addMouseListener(new JTableMouseListener());
		servReqTable.setModel(servReqTableM);

		JScrollPane jsp = new JScrollPane(servReqTable);
		servRequest.add(jsp);
	}

	public void createServiceList() {
		// 정비 정보 패널 생성
		serviceList.setBackground(new Color(225, 230, 246));
		serviceList.setLayout(new BorderLayout());
		String condition = "";
		// condition = "where CUSADDR like \'%Gunsan%\'";

		servListTable = new JTable();
		servListTableM = new DefaultTableModel();

		list7 = new ArrayList<>();

		ArrayList<DescribeTableVO> arrDescribeTableVO = mDBConn.getTableDescription("ServicesList");
		Object[] columnsName = new Object[arrDescribeTableVO.size()];
		for (int i = 0; i < arrDescribeTableVO.size(); i++) {
			columnsName[i] = arrDescribeTableVO.get(i).getColumn_name();
		}
		servListTableM.setColumnIdentifiers(columnsName);

		Object[] rowData = new Object[arrDescribeTableVO.size()];
		System.out.println(arrDescribeTableVO.size());
		ArrayList<ServicesList> arrServicesListVO = mDBConn.selectServicesList(condition);
		for (int i = 0; i < arrServicesListVO.size(); i++) {
			System.out.println(arrServicesListVO.get(i).getServiceID());
			if (isAdmin)
				rowData[0] = arrServicesListVO.get(i).getServiceID();
			else
				rowData[0] = "INVISIBLE";
			list7.add(arrServicesListVO.get(i).getServiceID());
			rowData[1] = arrServicesListVO.get(i).getCampCarID();
			rowData[2] = arrServicesListVO.get(i).getServCenterID();
			rowData[3] = arrServicesListVO.get(i).getRentCompID();
			rowData[4] = arrServicesListVO.get(i).getCusLicNum();
			rowData[5] = arrServicesListVO.get(i).getServiceItem();
			rowData[6] = arrServicesListVO.get(i).getServDate();
			rowData[7] = arrServicesListVO.get(i).getServBill();
			rowData[8] = arrServicesListVO.get(i).getBillDate();
			rowData[9] = arrServicesListVO.get(i).getOtherServItem();

			servListTableM.addRow(rowData);
		}

		servListTable.addMouseListener(new JTableMouseListener());
		servListTable.setModel(servListTableM);

		JScrollPane jsp = new JScrollPane(servListTable);
		serviceList.add(jsp);
	}

	public int confirmDlg(String msg) { //항목 변경 또는 삭제시 경고창
		return JOptionPane.showConfirmDialog(null, "이 항목을 " + msg + "하시겠습니까?", "경고!",
				JOptionPane.OK_CANCEL_OPTION);
	}

	public int InitDBconfirmDlg() { //데이터베이스 초기화 경고창
		return JOptionPane.showConfirmDialog(null, "데이터베이스를 초기화 하시겠습니까?", "경고!",
				JOptionPane.OK_CANCEL_OPTION);
	}

	public void alertPlsSelectItem() { //항목이 선택이 안 되었을 시 경고창
		JOptionPane.showMessageDialog(null, "항목 하나를 선택해주세요.", "경고!", JOptionPane.WARNING_MESSAGE);
	}

	public void alertNoOneTo(String msg) { //항목이 하나도 없는 경우 경고창
		JOptionPane.showMessageDialog(null, msg+"할 항목이 없습니다.", "경고!", JOptionPane.WARNING_MESSAGE);
	}

	public void actionUpdate(int arg0) { //업데이트(변경) 탭 인덱스를 인자로 받아서 테이블에 있는 값들 중 선택한 값을 변경함.
		if (arg0 == 0) {
			if (cusTable.getRowCount() == 0) {
				alertNoOneTo("업데이트");
				return;
			} else if (cusTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("업데이트(변경)") == 2)
				return;

			mDBConn.updateCustomer(list1.get(cusTable.getSelectedRow()),
					cusTableM.getValueAt(cusTable.getSelectedRow(), 0).toString(),
					cusTableM.getValueAt(cusTable.getSelectedRow(), 1).toString(),
					cusTableM.getValueAt(cusTable.getSelectedRow(), 2).toString(),
					cusTableM.getValueAt(cusTable.getSelectedRow(), 3).toString(),
					cusTableM.getValueAt(cusTable.getSelectedRow(), 4).toString(),
					cusTableM.getValueAt(cusTable.getSelectedRow(), 5).toString(),
					cusTableM.getValueAt(cusTable.getSelectedRow(), 6).toString());
			list1.clear();

		} else if (arg0 == 1) {
			if (carCompTable.getRowCount() == 0) {
				alertNoOneTo("업데이트");
				return;
			} else if (carCompTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("업데이트(변경)") == 2)
				return;

			mDBConn.updateCompanyInfo(list2.get(carCompTable.getSelectedRow()),
					carCompTableM.getValueAt(carCompTable.getSelectedRow(), 0).toString(),
					carCompTableM.getValueAt(carCompTable.getSelectedRow(), 1).toString(),
					carCompTableM.getValueAt(carCompTable.getSelectedRow(), 2).toString(),
					carCompTableM.getValueAt(carCompTable.getSelectedRow(), 3).toString(),
					carCompTableM.getValueAt(carCompTable.getSelectedRow(), 4).toString(),
					carCompTableM.getValueAt(carCompTable.getSelectedRow(), 5).toString());
			list2.clear();

		} else if (arg0 == 2) {
			if (servCentTable.getRowCount() == 0) {
				alertNoOneTo("업데이트");
				return;
			} else if (servCentTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("업데이트(변경)") == 2)
				return;

			mDBConn.updateServiceCenterInfo(list3.get(servCentTable.getSelectedRow()),
					servCentTableM.getValueAt(servCentTable.getSelectedRow(), 0).toString(),
					servCentTableM.getValueAt(servCentTable.getSelectedRow(), 1).toString(),
					servCentTableM.getValueAt(servCentTable.getSelectedRow(), 2).toString(),
					servCentTableM.getValueAt(servCentTable.getSelectedRow(), 3).toString(),
					servCentTableM.getValueAt(servCentTable.getSelectedRow(), 4).toString(),
					servCentTableM.getValueAt(servCentTable.getSelectedRow(), 5).toString());
			list3.clear();

		} else if (arg0 == 3) {
			if (campCarTable.getRowCount() == 0) {
				alertNoOneTo("업데이트");
				return;
			} else if (campCarTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("업데이트(변경)") == 2)
				return;

			mDBConn.updateCampCarInfo(list4.get(campCarTable.getSelectedRow()),
					campCarTableM.getValueAt(campCarTable.getSelectedRow(), 0).toString(),
					campCarTableM.getValueAt(campCarTable.getSelectedRow(), 1).toString(),
					campCarTableM.getValueAt(campCarTable.getSelectedRow(), 2).toString(),
					campCarTableM.getValueAt(campCarTable.getSelectedRow(), 3).toString(),
					Integer.parseInt(campCarTableM.getValueAt(campCarTable.getSelectedRow(), 4).toString()),
					campCarTableM.getValueAt(campCarTable.getSelectedRow(), 5).toString(),
					campCarTableM.getValueAt(campCarTable.getSelectedRow(), 6).toString(),
					Integer.parseInt(campCarTableM.getValueAt(campCarTable.getSelectedRow(), 7).toString()),
					campCarTableM.getValueAt(campCarTable.getSelectedRow(), 8).toString());
			list4.clear();

		} else if (arg0 == 4) {
			if (rentTable.getRowCount() == 0) {
				alertNoOneTo("업데이트");
				return;
			} else if (rentTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("업데이트(변경)") == 2)
				return;

			mDBConn.updateRentInfo(list5.get(rentTable.getSelectedRow()),
					rentTableM.getValueAt(rentTable.getSelectedRow(), 0).toString(),
					rentTableM.getValueAt(rentTable.getSelectedRow(), 1).toString(),
					rentTableM.getValueAt(rentTable.getSelectedRow(), 2).toString(),
					rentTableM.getValueAt(rentTable.getSelectedRow(), 3).toString(),
					rentTableM.getValueAt(rentTable.getSelectedRow(), 4).toString(),
					Integer.parseInt(rentTableM.getValueAt(rentTable.getSelectedRow(), 5).toString()),
					Integer.parseInt(rentTableM.getValueAt(rentTable.getSelectedRow(), 6).toString()),
					rentTableM.getValueAt(rentTable.getSelectedRow(), 7).toString(),
					rentTableM.getValueAt(rentTable.getSelectedRow(), 8).toString(),
					Integer.parseInt(rentTableM.getValueAt(rentTable.getSelectedRow(), 9).toString()));
			list5.clear();

		} else if (arg0 == 5) {
			if (servReqTable.getRowCount() == 0) {
				alertNoOneTo("업데이트");
				return;
			} else if (servReqTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("업데이트(변경)") == 2)
				return;

			mDBConn.updateRequstsService(list6.get(servReqTable.getSelectedRow()),
					servReqTableM.getValueAt(servReqTable.getSelectedRow(), 0).toString(),
					servReqTableM.getValueAt(servReqTable.getSelectedRow(), 1).toString(),
					servReqTableM.getValueAt(servReqTable.getSelectedRow(), 2).toString(),
					servReqTableM.getValueAt(servReqTable.getSelectedRow(), 3).toString(),
					servReqTableM.getValueAt(servReqTable.getSelectedRow(), 4).toString());
			list6.clear();

		} else if (arg0 == 6) {
			if (servListTable.getRowCount() == 0) {
				alertNoOneTo("업데이트");
				return;
			} else if (servListTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("업데이트(변경)") == 2)
				return;

			mDBConn.updateServiceInfo(list7.get(servListTable.getSelectedRow()),
					servListTableM.getValueAt(servListTable.getSelectedRow(), 0).toString(),
					servListTableM.getValueAt(servListTable.getSelectedRow(), 1).toString(),
					servListTableM.getValueAt(servListTable.getSelectedRow(), 2).toString(),
					servListTableM.getValueAt(servListTable.getSelectedRow(), 3).toString(),
					servListTableM.getValueAt(servListTable.getSelectedRow(), 4).toString(),
					servListTableM.getValueAt(servListTable.getSelectedRow(), 5).toString(),
					servListTableM.getValueAt(servListTable.getSelectedRow(), 6).toString(),
					Integer.parseInt(servListTable.getValueAt(servListTable.getSelectedRow(), 7).toString()),
					servListTableM.getValueAt(servListTable.getSelectedRow(), 8).toString(),
					servListTableM.getValueAt(servListTable.getSelectedRow(), 9).toString());
			list7.clear();
		}
	}

	public void actionDelete(int arg0) { /*데이터 삭제, 업데이트와 마찬가지로 탭 인덱스를 인자로 받아서, 
	해당 테이블의 선택한 투플의 첫 열(기본키 속성)을 조건으로 검색하여 삭제.*/
		System.out.println(cusTable.getSelectedRow() + "");
		if (arg0 == 0) {
			if (cusTable.getRowCount() == 0) {
				alertNoOneTo("삭제");
				return;
			} else if (cusTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("삭제") == 2)
				return;

			mDBConn.deleteTuple("CUSTOMER", list1.get(cusTable.getSelectedRow()), cusTableM.getColumnName(0));
			list1.clear();
		} else if (arg0 == 1) {
			if (carCompTable.getRowCount() == 0) {
				alertNoOneTo("삭제");
				return;
			} else if (carCompTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("삭제") == 2)
				return;

			mDBConn.deleteTuple("RENTALCOMPANY", list2.get(carCompTable.getSelectedRow()), carCompTableM.getColumnName(0));
			list2.clear();
		} else if (arg0 == 2) {
			if (servCentTable.getRowCount() == 0) {
				alertNoOneTo("삭제");
				return;
			} else if (servCentTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("삭제") == 2)
				return;

			mDBConn.deleteTuple("CARSERVICECENTER", list3.get(servCentTable.getSelectedRow()), servCentTableM.getColumnName(0));
			list3.clear();
		} else if (arg0 == 3) {
			if (campCarTable.getRowCount() == 0) {
				alertNoOneTo("삭제");
				return;
			} else if (campCarTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("삭제") == 2)
				return;

			mDBConn.deleteTuple("CAMPINGCAR", list4.get(campCarTable.getSelectedRow()), campCarTableM.getColumnName(0));
			list4.clear();
		} else if (arg0 == 4) {
			if (rentTable.getRowCount() == 0) {
				alertNoOneTo("삭제");
				return;
			} else if (rentTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("삭제") == 2)
				return;

			mDBConn.deleteTuple("RENTLIST", list5.get(rentTable.getSelectedRow()), rentTableM.getColumnName(0));
			list5.clear();
		} else if (arg0 == 5) {
			if (servReqTable.getRowCount() == 0) {
				alertNoOneTo("삭제");
				return;
			} else if (servReqTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("삭제") == 2)
				return;

			mDBConn.deleteTuple("REQUIRESERVICELIST", list6.get(servReqTable.getSelectedRow()), servReqTableM.getColumnName(0));
			list6.clear();
		} else if (arg0 == 6) {
			if (servListTable.getRowCount() == 0) {
				alertNoOneTo("삭제");
				return;
			} else if (servListTable.getSelectedRow() == -1) {
				alertPlsSelectItem();
				return;
			} else if (confirmDlg("삭제") == 2)
				return;

			mDBConn.deleteTuple("SERVICESLIST", list7.get(servListTable.getSelectedRow()), servListTableM.getColumnName(0));
			list7.clear();
		}
	}
	
	public ArrayList<String> fileStreamCreate() {
		ArrayList<String> fileStreams = new ArrayList<String> ();
		
		String sqls[] = {
				"SELECT COUNT(*) FROM all_tables WHERE table_name='CUSTOMER'",
				"SELECT COUNT(*) FROM all_tables WHERE table_name='RENTALCOMPANY'",
				"SELECT COUNT(*) FROM all_tables WHERE table_name='CARSERVICECENTER'",
				"SELECT COUNT(*) FROM all_tables WHERE table_name='CAMPINGCAR'",
				"SELECT COUNT(*) FROM all_tables WHERE table_name='RENTLIST'",
				"SELECT COUNT(*) FROM all_tables WHERE table_name='REQUIRESERVICELIST'",
				"SELECT COUNT(*) FROM all_tables WHERE table_name='SERVICESLIST'"};

		PreparedStatement pstmt = null;

		for(String sql:sqls) {
			try {
				pstmt = mDBConn.conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
	
				while (rs.next()) {
					if(sql.contains("CUSTOMER")) {
						if(rs.getInt(1) == 1) {
							fileStreams.add("UNSETFKREQUIRESERVICELIST.sql");
							fileStreams.add("UNSETFKSERVICESLIST.sql");
							fileStreams.add("UNSETFKRENTLIST.sql");
							fileStreams.add("DROPCUSTOMER.sql");
							fileStreams.add("CUSTOMER.sql");
							break;
						} else {
							fileStreams.add("CUSTOMER.sql");
							break;
						}
					}else if(sql.contains("RENTALCOMPANY")) {
						if(rs.getInt(1) == 1) {
							fileStreams.add("UNSETFKCAMPINGCAR.sql");
							fileStreams.add("DROPRENTALCOMPANY.sql");
							fileStreams.add("RENTALCOMPANY.sql");
							break;
						} else {
							fileStreams.add("RENTALCOMPANY.sql");
							break;
						}
					}else if(sql.contains("CARSERVICECENTER")) {
						if(rs.getInt(1) == 1) {
							fileStreams.add("DROPCARSERVICECENTER.sql");
							fileStreams.add("CARSERVICECENTER.sql");
							break;
						} else {
							fileStreams.add("CARSERVICECENTER.sql");
							break;
						}
					}else if(sql.contains("CAMPINGCAR")) {
						if(rs.getInt(1) == 1) {
							fileStreams.add("DROPCAMPINGCAR.sql");
							fileStreams.add("CAMPINGCAR.sql");
							fileStreams.add("SETFKCAMPINGCAR.sql");
							break;
						} else {
							fileStreams.add("CAMPINGCAR.sql");
							fileStreams.add("SETFKCAMPINGCAR.sql");
							break;
						}
					}else if(sql.contains("RENTLIST")) {
						if(rs.getInt(1) == 1) {
							fileStreams.add("DROPRENTLIST.sql");
							fileStreams.add("RENTLIST.sql");
							fileStreams.add("SETFKRENTLIST.sql");
							break;
						} else {
							fileStreams.add("RENTLIST.sql");
							fileStreams.add("SETFKRENTLIST.sql");
							break;
						}
					}else if(sql.contains("REQUIRESERVICELIST")) {
						if(rs.getInt(1) == 1) {
							fileStreams.add("DROPREQUIRESERVICELIST.sql");
							fileStreams.add("REQUIRESERVICELIST.sql");
							fileStreams.add("SETFKREQUIRESERVICELIST.sql");
							break;
						} else {
							fileStreams.add("REQUIRESERVICELIST.sql");
							fileStreams.add("SETFKREQUIRESERVICELIST.sql");
							break;
						}
					}else if(sql.contains("SERVICESLIST")) {
						if(rs.getInt(1) == 1) {
							fileStreams.add("DROPSERVICESLIST.sql");
							fileStreams.add("SERVICESLIST.sql");
							fileStreams.add("SETFKSERVICESLIST.sql");
							break;
						} else {
							fileStreams.add("SERVICESLIST.sql");
							fileStreams.add("SETFKSERVICESLIST.sql");
							break;
						}
					}
				}
	
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed())
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return fileStreams;
	}

	public void actionDBInit(int arg0) { //DB 초기화, sql파일을 실행하며, 프로젝트 폴더 내에 dbsql폴더에 위치한다.
		/**** 소스 참조: https://coderanch.com/t/306966/databases/Execute-sql-file-java ****/
		ArrayList<String> fileNames = fileStreamCreate();
		
		//sql 파일 이름
		int cnt = 0;
		String mTitle = "";
		String errMsg = "";
		for(String fnames:fileNames) { //파일 수 만큼 반복 실행
			String s = new String();
			StringBuffer sb = new StringBuffer();
			System.out.println(fnames);
			try {
				FileReader fr = new FileReader(new File("dbsql/"+fnames));
	
				BufferedReader br = new BufferedReader(fr);
	
				while ((s = br.readLine()) != null) {
					sb.append(s);
				}
				br.close();
	
				String[] inst = sb.toString().split(";");
				Statement st = mDBConn.conn.createStatement();
	
				for (int i = 0; i < inst.length; i++) {
					if (!inst[i].trim().equals("")) {
						st.executeUpdate(inst[i]);
						System.out.println(">>" + inst[i]);
					}
				}

			} catch (Exception e) {
				System.out.println("*** Error : " + e.toString());
				System.out.println("*** ");
				System.out.println("*** Error : ");
				e.printStackTrace();
				System.out.println("################################################");
				System.out.println(sb.toString());
				errMsg += e.toString();
				
				cnt++;
			}
		}
		if(cnt > 0)
			mTitle = "오류가 발생했습니다!: " + errMsg;
		else
			mTitle = "초기화가 완료되었습니다!";
		System.out.println("Reloading tables...");
		refreshTable();
		JOptionPane.showMessageDialog(null, mTitle, "알림", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) { //상단 메뉴 버튼 이벤트들
		// TODO Auto-generated method stub
		if (arg0.getSource().equals(btnSearch)) { //검색
			new SearchDialog();
		} else if (arg0.getSource().equals(btnRefresh)) { //재조회
			refreshTable(mJTabbedPane.getSelectedIndex());
		} else if (arg0.getSource().equals(btnAdd)) { //삽입
			insertDlg = new InsertDialog("입력", mJTabbedPane.getSelectedIndex());
		} else if (arg0.getSource().equals(btnQUpdate)) { //변경
			actionUpdate(mJTabbedPane.getSelectedIndex());
			refreshTable(mJTabbedPane.getSelectedIndex());
		} else if (arg0.getSource().equals(btnDelete)) { //삭제
			actionDelete(mJTabbedPane.getSelectedIndex());
			refreshTable(mJTabbedPane.getSelectedIndex());
		} else if (arg0.getSource().equals(btnInitDB)) { //데베 초기화
			if (InitDBconfirmDlg() == 0) {
				actionDBInit(0);
			}
		}
	}

	private class JTableMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	public void actionSearch(int arg0, int arg1, int arg2) { //검색 5개를 인자로 받음
		SearchPane = new JPanel();
		SearchTable = new JTable();

		SearchPane.setBackground(new Color(225, 230, 246));
		SearchPane.setLayout(new BorderLayout());

		if (mJTabbedPane.getTabCount() > 7) {
			SearchPane.removeAll();
			mJTabbedPane.remove(7);
		}
		
		if (arg0 == 0) { // Search 1
			String colnames[] = {"대여회사 ID", "대여 횟수", "평균 대여비용"};
			SearchTableM = new DefaultTableModel(colnames, 0);

			String sql = "SELECT COMPID, COUNT(*), AVG(RENTALBILL)" + 
					" FROM RENTLIST, RENTALCOMPANY" + 
					" WHERE COMPID = RENTCOMPID" + 
					" GROUP BY COMPID" + 
					" HAVING COUNT(*) >= 2";

			PreparedStatement pstmt = null;

			try {
				pstmt = mDBConn.conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					SearchTableM.addRow(
							new Object[] {
									rs.getString(1),
									rs.getInt(2),
									rs.getDouble(3)
									});
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed())
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} else if(arg0 == 1) { // Search 2
			String colnames[] = {"대여회사 ID", "상호명", "회사 주소", "회사 전화번호", "담당자 성함", "담당자 이메일"};
			SearchTableM = new DefaultTableModel(colnames, 0);

			String sql = "SELECT *" + 
					" FROM RENTALCOMPANY" + 
					" WHERE COMPID NOT IN" + 
					" (SELECT RENTCOMPID" + 
					" FROM SERVICESLIST)";

			PreparedStatement pstmt = null;

			try {
				pstmt = mDBConn.conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					SearchTableM.addRow(
							new Object[] {
									rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getString(6)
							});
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed())
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else if(arg0 == 2) { // Search 3
			String colnames[] = {"대여회사 ID", "상호명", "캠핑카 ID", "차량 이름", "차량 번호", "승차인원수", "대여비용"};
			SearchTableM = new DefaultTableModel(colnames, 0);

			String sql = "SELECT COMPID, COMPNAME, CARID, CARNAME, CARSNUM, NOPASSENGERS, RENTALFEE" + 
					" FROM RENTALCOMPANY, CAMPINGCAR" + 
					" WHERE COMPANYID = COMPID AND COMPID = '001'" + 
					" ORDER BY NOPASSENGERS DESC";

			PreparedStatement pstmt = null;

			try {
				pstmt = mDBConn.conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					SearchTableM.addRow(
							new Object[] {
									rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getInt(6),
									rs.getInt(7)
							});
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed())
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else if(arg0 == 3) { // Search 4
			String colnames[] = {"대여회사 ID", "대여 횟수", "평균 대여비용"};
			SearchTableM = new DefaultTableModel(colnames, 0);

			String sql = "SELECT COMPANYID, COUNT(*), AVG(RENTALFEE)" + 
					" FROM CAMPINGCAR" + 
					" GROUP BY COMPANYID, CARDETAILS1" + 
					" HAVING CARDETAILS1 LIKE '%4wd%'";

			PreparedStatement pstmt = null;

			try {
				pstmt = mDBConn.conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					SearchTableM.addRow(
							new Object[] {
									rs.getString(1),
									rs.getInt(2),
									rs.getDouble(3)
							});
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed())
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else if(arg0 == 4) { // Search 5
			String colnames[] = {"대여회사 ID", "상호명", "회사 주소", "회사 전화번호", "담당자 성함", "담당자 이메일"};
			SearchTableM = new DefaultTableModel(colnames, 0);

			String sql = "SELECT *" + 
					" FROM RENTALCOMPANY" +
					" WHERE COMPID IN" + 
					" (SELECT COMPANYID" + 
					" FROM CAMPINGCAR" + 
					" WHERE RENTALFEE >= "+arg1+" AND RENTALFEE < "+arg2+")";

			PreparedStatement pstmt = null;

			try {
				pstmt = mDBConn.conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					SearchTableM.addRow(
							new Object[] {
									rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getString(6)
							});
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null && !pstmt.isClosed())
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//완료되면 검색결과 패널을 만들고 검색결과...탭을 생성하여 붙임
		SearchTable.setModel(SearchTableM);

		JScrollPane jsp = new JScrollPane(SearchTable);
		SearchPane.add(jsp);
		mJTabbedPane.addTab("검색 결과...", SearchPane);
		mJTabbedPane.setSelectedIndex(7);
	}

	public void refreshTable() { //전체 테이블 재조회, 컴포넌트들을 제거하고 다시 생성
		cusInfo.removeAll();
		carCompInfo.removeAll();
		servCenterInfo.removeAll();
		campCarInfo.removeAll();
		rentList.removeAll();
		servRequest.removeAll();
		serviceList.removeAll();
		createCusInfo();
		createCarCompInfo();
		createServCenterInfo();
		createCampCarInfo();
		createRentList();
		createServRequest();
		createServiceList();
		repaint();
	}

	public void refreshTable(int arg0) { //부분 재조회
		if (arg0 == 0) {
			cusInfo.removeAll();
			createCusInfo();
			repaint();
		} else if (arg0 == 1) {
			carCompInfo.removeAll();
			createCarCompInfo();
			repaint();
		} else if (arg0 == 2) {
			servCenterInfo.removeAll();
			createServCenterInfo();
			repaint();
		} else if (arg0 == 3) {
			campCarInfo.removeAll();
			createCampCarInfo();
			repaint();
		} else if (arg0 == 4) {
			rentList.removeAll();
			createRentList();
			repaint();
		} else if (arg0 == 5) {
			servRequest.removeAll();
			createServRequest();
			repaint();
		} else if (arg0 == 6) {
			serviceList.removeAll();
			createServiceList();
			repaint();
		}
	}
	
	class SearchInput extends JDialog {
		JPanel contentPane, center, footer;
		JLabel lbfrom, lbto;
		JButton btnsearch, btncancel;
		JTextField txfrom, txto;
		
		public SearchInput() {
			contentPane = new JPanel();
			center = new JPanel();
			footer = new JPanel();
			contentPane.setLayout(new BorderLayout());
			center.setLayout(new GridLayout(1, 4));
			footer.setLayout(new FlowLayout(FlowLayout.RIGHT));
			lbfrom = new JLabel("부터(이상): ", SwingConstants.RIGHT);
			lbto = new JLabel("까지(미만): ", SwingConstants.RIGHT);
			btnsearch = new JButton("Search!");
			btncancel = new JButton("Cancel");
			txfrom = new JTextField();
			txto = new JTextField();
			
			center.add(lbfrom);
			center.add(txfrom);
			center.add(lbto);
			center.add(txto);
			footer.add(btnsearch);
			footer.add(btncancel);
			contentPane.add(center, BorderLayout.CENTER);
			contentPane.add(footer, BorderLayout.SOUTH);
			
			this.add(contentPane);
			this.setTitle("가격대 입력");
			this.setSize(550, 100);
			this.setLocation(100, 100);
			this.setVisible(true);
			
			btncancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					SearchInput.this.dispose();
				}
			});
			
			btnsearch.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					actionSearch(4, Integer.parseInt(txfrom.getText().toString()), Integer.parseInt(txto.getText().toString()));
				}
			});
		}
	}

	class SearchDialog extends JDialog implements ActionListener{ //검색 대화창, 원하는 검색을 버튼으로 선택할 수 있다.
		JPanel contentPane;
		JButton btnFc1, btnFc2, btnFc3, btnFc4, btnFc5;
		
		public SearchDialog() {
			contentPane = new JPanel();
			contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			btnFc1 = new JButton("검색1");
			btnFc2 = new JButton("검색2");
			btnFc3 = new JButton("검색3");
			btnFc4 = new JButton("검색4");
			btnFc5 = new JButton("검색5");
			
			contentPane.add(btnFc1);
			contentPane.add(btnFc2);
			contentPane.add(btnFc3);
			contentPane.add(btnFc4);
			contentPane.add(btnFc5);
			
			btnFc1.addActionListener(this);
			btnFc2.addActionListener(this);
			btnFc3.addActionListener(this);
			btnFc4.addActionListener(this);
			btnFc5.addActionListener(this);
			
			this.add(contentPane);
			this.setSize(360, 100);
			this.setLocation(100, 100);
			this.setTitle("검색할 버튼을 선택해주세요.");
			this.setModal(false);
			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource().equals(btnFc1))
				actionSearch(0, 0, 0);
			else if(e.getSource().equals(btnFc2))
				actionSearch(1, 0, 0);
			else if(e.getSource().equals(btnFc3))
				actionSearch(2, 0, 0);
			else if(e.getSource().equals(btnFc4))
				actionSearch(3, 0, 0);
			else if(e.getSource().equals(btnFc5))
				new SearchInput();
			
			SearchDialog.this.dispose();
		}
	}
	
	class InsertDialog extends JDialog { //데이터 삽입 대화창, 탭 인덱스를 받아서 각 테이블에 맞는 값을 넣을 수 있는 레이아웃 생성.
		//insert버튼을 누르면 DB커넥터 클래스의 각 테이블 insert메서드를 실행.
		JPanel contentPane;
		JPanel footerPane;
		JButton btncancel;

		public InsertDialog(String mTitle, int arg0) {
			contentPane = new JPanel();
			footerPane = new JPanel();
			footerPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			btncancel = new JButton("Cancel");
			mDBConn = new DatabaseConnect();

			setLayout(new BorderLayout());

			if (arg0 == 0) {
				setLayoutCusInfo();
			} else if (arg0 == 1) {
				setLayoutCompInfo();
			} else if (arg0 == 2) {
				setLayoutCarcenter();
			} else if (arg0 == 3) {
				setLayoutCampingCar();
			} else if (arg0 == 4) {
				setLayoutRentList();
			} else if (arg0 == 5) {
				setLayoutRequestService();
			} else if (arg0 == 6) {
				setLayoutServicesList();
			}

			btncancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					InsertDialog.this.dispose();
				}
			});

			this.add(contentPane, BorderLayout.CENTER);
			this.add(footerPane, BorderLayout.SOUTH);
			this.setLocation(100, 100);
			this.setModal(false);
			this.setVisible(true);

		}

		public void setLayoutCusInfo() {
			setTitle("고객 정보 입력");
			JLabel lblicnum, lbname, lbaddr, lbphoneno, lbemail, lbprevdate, lbprevcarid;
			JTextField txlicnum, txname, txaddr, txphoneno, txemail, txprevdate, txprevcarid;
			JButton btninsert;
			lblicnum = new JLabel("운전면허증 번호");
			lbname = new JLabel("성함");
			lbaddr = new JLabel("주소");
			lbphoneno = new JLabel("연락처");
			lbemail = new JLabel("이메일");
			lbprevdate = new JLabel("이전 이용 날짜");
			lbprevcarid = new JLabel("이전 이용 캠핑카 종류");
			btninsert = new JButton("Insert!");

			txlicnum = new JTextField();
			txname = new JTextField();
			txaddr = new JTextField();
			txphoneno = new JTextField();
			txemail = new JTextField();
			txprevdate = new JTextField();
			txprevcarid = new JTextField();

			contentPane.setLayout(new GridLayout(2, 7));
			contentPane.add(lblicnum);
			contentPane.add(lbname);
			contentPane.add(lbaddr);
			contentPane.add(lbphoneno);
			contentPane.add(lbemail);
			contentPane.add(lbprevdate);
			contentPane.add(lbprevcarid);
			contentPane.add(txlicnum);
			contentPane.add(txname);
			contentPane.add(txaddr);
			contentPane.add(txphoneno);
			contentPane.add(txemail);
			contentPane.add(txprevdate);
			contentPane.add(txprevcarid);

			footerPane.add(btninsert);
			footerPane.add(btncancel);

			this.setSize(1000, 180);
			btninsert.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String a0 = txlicnum.getText().toString();
					String a1 = txname.getText().toString();
					String a2 = txaddr.getText().toString();
					String a3 = txphoneno.getText().toString();
					String a4 = txemail.getText().toString();
					String a5 = txprevdate.getText().toString();
					String a6 = txprevcarid.getText().toString();

					mDBConn.insertCustomerInfo(a0, a1, a2, a3, a4, a5, a6);
					InsertDialog.this.dispose();
					refreshTable(0);
				}
			});
		}

		public void setLayoutCompInfo() {
			setTitle("대여회사 정보 입력");
			JLabel lbid, lbname, lbaddr, lbphoneno, lbmanager, lbemail;
			JTextField txid, txname, txaddr, txphoneno, txmanager, txemail;
			JButton btninsert;
			lbid = new JLabel("회사ID");
			lbname = new JLabel("상호명");
			lbaddr = new JLabel("회사주소");
			lbphoneno = new JLabel("연락처");
			lbmanager = new JLabel("담당자 성함");
			lbemail = new JLabel("담당자 이메일");

			btninsert = new JButton("Insert!");
			btncancel = new JButton("Cancel");

			txid = new JTextField();
			txname = new JTextField();
			txaddr = new JTextField();
			txphoneno = new JTextField();
			txmanager = new JTextField();
			txemail = new JTextField();

			contentPane.setLayout(new GridLayout(2, 6));
			contentPane.add(lbid);
			contentPane.add(lbname);
			contentPane.add(lbaddr);
			contentPane.add(lbphoneno);
			contentPane.add(lbmanager);
			contentPane.add(lbemail);
			contentPane.add(txid);
			contentPane.add(txname);
			contentPane.add(txaddr);
			contentPane.add(txphoneno);
			contentPane.add(txmanager);
			contentPane.add(txemail);

			footerPane.add(btninsert);
			footerPane.add(btncancel);

			this.setSize(1000, 180);
			btninsert.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String a0 = txid.getText().toString();
					String a1 = txname.getText().toString();
					String a2 = txaddr.getText().toString();
					String a3 = txphoneno.getText().toString();
					String a4 = txmanager.getText().toString();
					String a5 = txemail.getText().toString();

					mDBConn.insertCompanyInfo(a0, a1, a2, a3, a4, a5);
					InsertDialog.this.dispose();
					refreshTable(1);
				}
			});
		}

		public void setLayoutCarcenter() {
			setTitle("정비소 정보 입력");
			JLabel lbcenterID, lbcenterName, lbcenterAddress, lbcenterPhoneNo, lbcenterManager, lbcenterManagerEmail;
			JTextField txcenterID, txcenterName, txcenterAddress, txcenterPhoneNo, txcenterManager,
					txcenterManagerEmail;
			JButton btninsert;
			lbcenterID = new JLabel("정비소ID");
			lbcenterName = new JLabel("정비소 이름");
			lbcenterAddress = new JLabel("정비소 주소");
			lbcenterPhoneNo = new JLabel("연락처");
			lbcenterManager = new JLabel("담당자 성함");
			lbcenterManagerEmail = new JLabel("담당자 이메일");
			btninsert = new JButton("Insert!");
			btncancel = new JButton("Cancel");

			txcenterID = new JTextField();
			txcenterName = new JTextField();
			txcenterAddress = new JTextField();
			txcenterPhoneNo = new JTextField();
			txcenterManager = new JTextField();
			txcenterManagerEmail = new JTextField();

			contentPane.setLayout(new GridLayout(2, 6));
			contentPane.add(lbcenterID);
			contentPane.add(lbcenterName);
			contentPane.add(lbcenterAddress);
			contentPane.add(lbcenterPhoneNo);
			contentPane.add(lbcenterManager);
			contentPane.add(lbcenterManagerEmail);
			contentPane.add(txcenterID);
			contentPane.add(txcenterName);
			contentPane.add(txcenterAddress);
			contentPane.add(txcenterPhoneNo);
			contentPane.add(txcenterManager);
			contentPane.add(txcenterManagerEmail);

			footerPane.add(btninsert);
			footerPane.add(btncancel);

			this.setSize(1000, 180);
			btninsert.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String a0 = txcenterID.getText().toString();
					String a1 = txcenterName.getText().toString();
					String a2 = txcenterAddress.getText().toString();
					String a3 = txcenterPhoneNo.getText().toString();
					String a4 = txcenterManager.getText().toString();
					String a5 = txcenterManagerEmail.getText().toString();

					mDBConn.insertServiceCenterInfo(a0, a1, a2, a3, a4, a5);
					InsertDialog.this.dispose();
					refreshTable(2);
				}
			});
		}

		public void setLayoutCampingCar() {
			setTitle("캠핑카 정보 입력");
			JLabel lbcampingcid, lbcompID, lbcampname, lbcarnum, lbridenum, lbinform1, lbinform2, lbrendcost, lbregiday;
			JTextField txcampingcid, txcompID, txcampname, txcarnum, txridenum, txinform1, txinform2, txrendcost,
					txregiday;
			JButton btninsert;

			lbcampingcid = new JLabel("캠핑카등록ID");
			lbcompID = new JLabel("회사ID");
			lbcampname = new JLabel("차량 이름");
			lbcarnum = new JLabel("차량 번호");
			lbridenum = new JLabel("승차인원수");
			lbinform1 = new JLabel("상세정보1");
			lbinform2 = new JLabel("상세정보2");
			lbrendcost = new JLabel("대여비용");
			lbregiday = new JLabel("등록일자");
			btninsert = new JButton("Insert!");
			btncancel = new JButton("Cancel");

			txcampingcid = new JTextField();
			txcompID = new JTextField();
			txcampname = new JTextField();
			txcarnum = new JTextField();
			txridenum = new JTextField();
			txinform1 = new JTextField();
			txinform2 = new JTextField();
			txrendcost = new JTextField();
			txregiday = new JTextField();

			contentPane.setLayout(new GridLayout(2, 9));
			contentPane.add(lbcampingcid);
			contentPane.add(lbcompID);
			contentPane.add(lbcampname);
			contentPane.add(lbcarnum);
			contentPane.add(lbridenum);
			contentPane.add(lbinform1);
			contentPane.add(lbinform2);
			contentPane.add(lbrendcost);
			contentPane.add(lbregiday);
			contentPane.add(txcampingcid);
			contentPane.add(txcompID);
			contentPane.add(txcampname);
			contentPane.add(txcarnum);
			contentPane.add(txridenum);
			contentPane.add(txinform1);
			contentPane.add(txinform2);
			contentPane.add(txrendcost);
			contentPane.add(txregiday);

			footerPane.add(btninsert);
			footerPane.add(btncancel);

			this.setSize(1000, 180);
			btninsert.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String a0 = txcampingcid.getText().toString();
					String a1 = txcompID.getText().toString();
					String a2 = txcampname.getText().toString();
					String a3 = txcarnum.getText().toString();
					int a4 = Integer.parseInt(txridenum.getText().toString());
					String a5 = txinform1.getText().toString();
					String a6 = txinform2.getText().toString();
					int a7 = Integer.parseInt(txrendcost.getText().toString());
					String a8 = txregiday.getText().toString();

					mDBConn.insertCampCarInfo(a0, a1, a2, a3, a4, a5, a6, a7, a8);
					InsertDialog.this.dispose();
					refreshTable(3);
				}
			});
		}

		public void setLayoutRentList() {
			setTitle("대여 정보 입력");
			JLabel lbrentalID, lbcampCarID, lbcusLicNum, lbrentCompID, lbstartDate, lbrentalPeriod, lbrentalBill,
					lbbillDate, lbothers, lbextBill;
			JTextField txrentalID, txcampCarID, txcusLicNum, txrentCompID, txstartDate, txrentalPeriod, txrentalBill,
					txbillDate, txothers, txextBill;
			JButton btninsert;

			lbrentalID = new JLabel("대여번호");
			lbcampCarID = new JLabel("차량ID");
			lbcusLicNum = new JLabel("고객 운전면허번호");
			lbrentCompID = new JLabel("대여회사ID");
			lbstartDate = new JLabel("대여시작일");
			lbrentalPeriod = new JLabel("대여기간(일)");
			lbrentalBill = new JLabel("청구요금");
			lbbillDate = new JLabel("납입기한");
			lbothers = new JLabel("기타 청구내역");
			lbextBill = new JLabel("기타 청구요금");
			btninsert = new JButton("Insert!");
			btncancel = new JButton("Cancel");

			txrentalID = new JTextField();
			txcampCarID = new JTextField();
			txcusLicNum = new JTextField();
			txrentCompID = new JTextField();
			txstartDate = new JTextField();
			txrentalPeriod = new JTextField();
			txrentalBill = new JTextField();
			txbillDate = new JTextField();
			txothers = new JTextField();
			txextBill = new JTextField();

			contentPane.setLayout(new GridLayout(2, 10));
			contentPane.add(lbrentalID);
			contentPane.add(lbcampCarID);
			contentPane.add(lbcusLicNum);
			contentPane.add(lbrentCompID);
			contentPane.add(lbstartDate);
			contentPane.add(lbrentalPeriod);
			contentPane.add(lbrentalBill);
			contentPane.add(lbbillDate);
			contentPane.add(lbothers);
			contentPane.add(lbextBill);

			contentPane.add(txrentalID);
			contentPane.add(txcampCarID);
			contentPane.add(txcusLicNum);
			contentPane.add(txrentCompID);
			contentPane.add(txstartDate);
			contentPane.add(txrentalPeriod);
			contentPane.add(txrentalBill);
			contentPane.add(txothers);
			contentPane.add(txbillDate);
			contentPane.add(txextBill);

			footerPane.add(btninsert);
			footerPane.add(btncancel);

			this.setSize(1000, 180);
			btninsert.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String a0 = txrentalID.getText().toString();
					String a1 = txcampCarID.getText().toString();
					String a2 = txcusLicNum.getText().toString();
					String a3 = txrentCompID.getText().toString();
					String a4 = txstartDate.getText().toString();
					int a5 = Integer.parseInt(txrentalPeriod.getText().toString());
					int a6 = Integer.parseInt(txrentalBill.getText().toString());
					String a7 = txothers.getText().toString();
					String a8 = txbillDate.getText().toString();
					int a9 = Integer.parseInt(txextBill.getText().toString());

					mDBConn.insertRentInfo(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9);
					InsertDialog.this.dispose();
					refreshTable(4);
				}
			});
		}

		public void setLayoutRequestService() {
			setTitle("정비 요청 입력");
			JLabel lbreceiptID, lbcusLicNum, lbrentid, lbreceiptdate, lbphoneno;
			JTextField txreceiptID, txcusLicNum, txrentid, txreceiptdate, txphoneno;
			JButton btninsert;
			lbreceiptID = new JLabel("접수번호");
			lbcusLicNum = new JLabel("고객 운전면허번호");
			lbrentid = new JLabel("대여번호");
			lbreceiptdate = new JLabel("접수날짜");
			lbphoneno = new JLabel("고객 전화번호");
			btninsert = new JButton("Insert!");
			btncancel = new JButton("Cancel");

			txreceiptID = new JTextField();
			txcusLicNum = new JTextField();
			txrentid = new JTextField();
			txreceiptdate = new JTextField();
			txphoneno = new JTextField();

			contentPane.setLayout(new GridLayout(2, 5));
			contentPane.add(lbreceiptID);
			contentPane.add(lbcusLicNum);
			contentPane.add(lbrentid);
			contentPane.add(lbreceiptdate);
			contentPane.add(lbphoneno);
			contentPane.add(txreceiptID);
			contentPane.add(txcusLicNum);
			contentPane.add(txrentid);
			contentPane.add(txreceiptdate);
			contentPane.add(txphoneno);

			footerPane.add(btninsert);
			footerPane.add(btncancel);

			this.setSize(1000, 180);
			btninsert.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String a0 = txreceiptID.getText().toString();
					String a1 = txcusLicNum.getText().toString();
					String a2 = txrentid.getText().toString();
					String a3 = txreceiptdate.getText().toString();
					String a4 = txphoneno.getText().toString();

					mDBConn.insertRequstsService(a0, a1, a2, a3, a4);
					InsertDialog.this.dispose();
					refreshTable(5);
				}
			});
		}

		public void setLayoutServicesList() {
			setTitle("정비 정보 입력");
			JLabel lbserviceID, lbcampCarID, lbservserviceID, lbrentCompID, lbcusLicNum, lbserviceItem, lbservDate,
					lbservBill, lbbillDate, lbotherServItem;
			JTextField txserviceID, txcampCarID, txservserviceID, txrentCompID, txcusLicNum, txserviceItem, txservDate,
					txservBill, txbillDate, txotherServItem;
			JButton btninsert;
			lbserviceID = new JLabel("정비번호");
			lbcampCarID = new JLabel("차량ID");
			lbservserviceID = new JLabel("정비소ID");
			lbrentCompID = new JLabel("대여회사ID");
			lbcusLicNum = new JLabel("고객 운전면허번호");
			lbserviceItem = new JLabel("정비내역");
			lbservDate = new JLabel("수리날짜");
			lbservBill = new JLabel("수리비용");
			lbbillDate = new JLabel("납입기한");
			lbotherServItem = new JLabel("기타 정비내역");
			btninsert = new JButton("Insert!");
			btncancel = new JButton("Cancel");

			txserviceID = new JTextField();
			txcampCarID = new JTextField();
			txservserviceID = new JTextField();
			txrentCompID = new JTextField();
			txcusLicNum = new JTextField();
			txserviceItem = new JTextField();
			txservDate = new JTextField();
			txservBill = new JTextField();
			txbillDate = new JTextField();
			txotherServItem = new JTextField();

			contentPane.setLayout(new GridLayout(2, 10));
			contentPane.add(lbserviceID);
			contentPane.add(lbcampCarID);
			contentPane.add(lbservserviceID);
			contentPane.add(lbrentCompID);
			contentPane.add(lbcusLicNum);
			contentPane.add(lbserviceItem);
			contentPane.add(lbservDate);
			contentPane.add(lbservBill);
			contentPane.add(lbbillDate);
			contentPane.add(lbotherServItem);
			contentPane.add(txserviceID);
			contentPane.add(txcampCarID);
			contentPane.add(txservserviceID);
			contentPane.add(txrentCompID);
			contentPane.add(txcusLicNum);
			contentPane.add(txserviceItem);
			contentPane.add(txservDate);
			contentPane.add(txservBill);
			contentPane.add(txbillDate);
			contentPane.add(txotherServItem);

			footerPane.add(btninsert);
			footerPane.add(btncancel);

			this.setSize(1000, 180);
			btninsert.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String a0 = txserviceID.getText().toString();
					String a1 = txcampCarID.getText().toString();
					String a2 = txservserviceID.getText().toString();
					String a3 = txrentCompID.getText().toString();
					String a4 = txcusLicNum.getText().toString();
					String a5 = txserviceItem.getText().toString();
					String a6 = txservDate.getText().toString();
					int a7 = Integer.parseInt(txservBill.getText().toString());
					String a8 = txbillDate.getText().toString();
					String a9 = txotherServItem.getText().toString();

					mDBConn.insertServiceInfo(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9);
					InsertDialog.this.dispose();
					refreshTable(6);
				}
			});
		}
	}
}

class CampingCarList {
	private String carID;
	private String companyID;
	private String carName;
	private String carSNum;
	private int noPassengers;
	private String carDetails1;
	private String carDetails2;
	private int rentalFee;
	private String regDate;
	
	public CampingCarList() {
	}
	
	public CampingCarList(String carID, String companyID, String carName, String carSNum, int noPassengers, String carDetails1, String carDetails2,
			int rentalFee,String regDate) {
		super();
		this.carID = carID;
		this.companyID = companyID;
		this.carName = carName;
		this.carSNum =  carSNum;
		this.noPassengers = noPassengers;
		this.carDetails1 = carDetails1;
		this.carDetails2 = carDetails2;
		this.rentalFee = rentalFee;
		this.regDate = regDate;
	}
	
	public String getCarID() {
		return carID;
	}

	public void setCarID(String carID) {
		this.carID = carID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getCarSNum() {
		return  carSNum;
	}

	public void setCarSNum(String carSNum) {
		this. carSNum =  carSNum;
	}

	public int getNoPassengers() {
		return noPassengers;
	}

	public void setNoPassengers(int noPassengers) {
		this.noPassengers = noPassengers;
	}
	
	public String getCarDetails1() {
		return carDetails1;
	}

	public void setCarDetails1(String carDetails1) {
		this.carDetails1 = carDetails1;
	}
	
	public String getCarDetails2() {
		return carDetails2;
	}

	public void setCarDetails2(String carDetails2) {
		this.carDetails2 = carDetails2;
	}
	
	public int getRentalFee() {
		return rentalFee;
	}

	public void setRentalFee(int rentalFee) {
		this.rentalFee = rentalFee;
	}
	
	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	
	@Override
	public String toString() {
		return "CampingCarList [carID=" + carID + ", companyID=" + companyID + ", carName=" + carName +
				", carSNum=" +  carSNum + ", noPassengers=" + noPassengers +", carDetails1="+ carDetails1 + 
				", carDetails2=" +  carDetails2 +", rentalFee=" +  rentalFee +", regDate=" +  regDate +"]";
	}	
}

class CarServiceCenter {
	private String centerID;
	private String centerName;
	private String centerAddress;
	private String centerPhoneNo;
	private String centerManager;
	private String centerManagerEmail;
	
	public CarServiceCenter() {
	}
	
	public CarServiceCenter(String centerID, String centerName, String centerAddress, String  centerPhoneNo, String centerManager, String centerManagerEmail) {
		super();
		this.centerID = centerID;
		this.centerName = centerName;
		this.centerAddress = centerAddress;
		this.centerPhoneNo =  centerPhoneNo;
		this.centerManager = centerManager;
		this.centerManagerEmail = centerManagerEmail;
	}
	public String getCenterID() {
		return centerID;
	}

	public void setCenterID(String centerID) {
		this.centerID = centerID;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getCenterAddress() {
		return centerAddress;
	}

	public void setCenterAddress(String centerAddress) {
		this.centerAddress = centerAddress;
	}

	public String getCenterPhoneNo() {
		return  centerPhoneNo;
	}

	public void setCenterPhoneNo(String centerPhoneNo) {
		this. centerPhoneNo =  centerPhoneNo;
	}

	public String getCenterManager() {
		return centerManager;
	}

	public void setCenterManager(String centerManager) {
		this.centerManager = centerManager;
	}
	
	public String getCenterManagerEmail() {
		return centerManagerEmail;
	}

	public void setCenterManagerEmail(String centerManagerEmail) {
		this.centerManagerEmail = centerManagerEmail;
	}

	
	@Override
	public String toString() {
		return "CarServiceCenter [centerID=" + centerID + ", centerName=" + centerName + ", centerAddress=" + centerAddress +
				", centerPhoneNo=" +  centerPhoneNo + ", centerManager=" + centerManager +", centerManagerEmail="+ centerManagerEmail + "]";
	}	
}

class CustomerInfo {
	private String licNum;
	private String cusName;
	private String cusAddress;
	private String cusPhoneNo;
	private String cusEmail;
	private String lastUseDate;
	private String lastCarID;
	
	public CustomerInfo() {
	}
	
	public CustomerInfo(String licNum, String cusName, String cusAddress, String cusPhoneNo, String cusEmail, String lastUseDate, String lastCarID) {
		super();
		this.licNum = licNum;
		this.cusName = cusName;
		this.cusAddress = cusAddress;
		this.cusPhoneNo = cusPhoneNo;
		this.cusEmail = cusEmail;
		this.lastUseDate = lastUseDate;
		this.lastCarID = lastCarID;
	}
	
	public String getLicNum() {
		return licNum;
	}

	public void setLicNum(String licNum) {
		this.licNum = licNum;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusAddress() {
		return cusAddress;
	}

	public void setCusAddress(String cusAddress) {
		this.cusAddress = cusAddress;
	}

	public String getCusPhoneNo() {
		return cusPhoneNo;
	}

	public void setCusPhoneNo(String cusPhoneNo) {
		this.cusPhoneNo = cusPhoneNo;
	}

	public String getCusEmail() {
		return cusEmail;
	}

	public void setCusEmail(String cusEmail) {
		this.cusEmail = cusEmail;
	}
	
	public String getLastUseDate() {
		return lastUseDate;
	}

	public void setLastUseData(String lastUseDate) {
		this.lastUseDate = lastUseDate;
	}

	public String getLastCarID() {
		return lastCarID;
	}

	public void setLastCarID(String lastCarID) {
		this.lastCarID = lastCarID;
	}

	@Override
	public String toString() {
		return "CustomerInfo [licNum=" + licNum + ", cusName=" + cusName + ", cuscusPhoneNo=" + cusAddress +
				",cusPhoneNo=" + cusPhoneNo + ", cusEmail=" + cusEmail +",lastUseDate="+ lastUseDate +
				",lastCarID="+ lastCarID + "]";
	}
}

class DatabaseConnect { //DB 연결 및 명령어들
	public Connection conn = null;
	private Statement stmt = null;

	public static final String USERNAME = "s14011032";
	public static final String PASSWORD = "stech081800";
	private static final String URL 
	= "jdbc:oracle:thin:@localhost:1521:orcl";

	public DatabaseConnect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			System.out.println("DB Connection OK!");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		}catch(SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
			
		}
	}
	
	public ArrayList<CustomerInfo> selectCustomer(String condition) {
		String sql = "SELECT * FROM CUSTOMER ";
		sql+=condition;
		PreparedStatement pstmt = null;
		ArrayList<CustomerInfo> arrCustomerVO = new ArrayList<CustomerInfo>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CustomerInfo tempCustomerVO = new CustomerInfo(rs.getString("LICNUM"),
						 rs.getString("CUSNAME"),
						 rs.getString("CUSADDRESS"),
						 rs.getString("CUSPHONE"),
						 rs.getString("CUSEMAIL"),
						 rs.getString("LASTUSEDATE"),
						 rs.getString("LASTCARID"));
				arrCustomerVO.add(tempCustomerVO);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return arrCustomerVO;
	}
	
	public ArrayList<RentalCompany> selectRentalCompany(String condition) {
		String sql = "SELECT * FROM RENTALCOMPANY ";
		sql+=condition;
		PreparedStatement pstmt = null;
		ArrayList<RentalCompany> arrRentalCompanyVO = new ArrayList<RentalCompany>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RentalCompany tempRentalCompanyVO = new RentalCompany(rs.getString("COMPID"),
						 rs.getString("COMPNAME"),
						 rs.getString("COMPADDRESS"),
						 rs.getString("COMPPHONENO"),
						 rs.getString("COMPMANAGER"),
						 rs.getString("COMPMANAGEREMAIL"));
				arrRentalCompanyVO.add(tempRentalCompanyVO);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return arrRentalCompanyVO;
	}
	
	public ArrayList<CarServiceCenter> selectServiceCenter(String condition) {
		String sql = "SELECT * FROM CARSERVICECENTER ";
		sql+=condition;
		PreparedStatement pstmt = null;
		ArrayList<CarServiceCenter> arrCarServiceCenterVO = new ArrayList<CarServiceCenter>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CarServiceCenter tempCarServiceCenterVO = new CarServiceCenter(rs.getString("CENTERID"),
						 rs.getString("CENTERNAME"),
						 rs.getString("CENTERADDRESS"),
						 rs.getString("CENTERPHONENO"),
						 rs.getString("CENTERMANAGER"),
						 rs.getString("CENTERMANAGEREMAIL"));
				arrCarServiceCenterVO.add(tempCarServiceCenterVO);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return arrCarServiceCenterVO;
	}
	
	public ArrayList<CampingCarList> selectCampingCarList(String condition) {
		String sql = "SELECT * FROM CAMPINGCAR ";
		sql+=condition;
		PreparedStatement pstmt = null;
		ArrayList<CampingCarList> arrCampingCarListVO = new ArrayList<CampingCarList>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CampingCarList tempCampingCarListVO = new CampingCarList(rs.getString("CARID"),
						 rs.getString("COMPANYID"),
						 rs.getString("CARNAME"),
						 rs.getString("CARSNUM"),
						 rs.getInt("NOPASSENGERS"),
						 rs.getString("CARDETAILS1"),
						 rs.getString("CARDETAILS2"),
						 rs.getInt("RENTALFEE"),
						 rs.getString("REGDATE"));
				arrCampingCarListVO.add(tempCampingCarListVO);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return arrCampingCarListVO;
	}
	
	public ArrayList<RentList> selectRentList(String condition) {
		String sql = "SELECT * FROM RENTLIST ";
		sql+=condition;
		PreparedStatement pstmt = null;
		ArrayList<RentList> arrRentListVO = new ArrayList<RentList>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RentList tempRentListVO = new RentList(rs.getString("RENTALID"),
						 rs.getString("CAMPCARID"),
						 rs.getString("CUSLICNUM"),
						 rs.getString("RENTCOMPID"),
						 rs.getString("STARTDATE"),
						 rs.getInt("RENTALPERIOD"),
						 rs.getInt("RENTALBILL"),
						 rs.getString("BILLDATE"),
						 rs.getString("OTHERS"),
						 rs.getInt("EXTBILL"));
				arrRentListVO.add(tempRentListVO);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return arrRentListVO;
	}
	
	public ArrayList<RequestServiceList> selectRequestServiceList(String condition) {
		String sql = "SELECT * FROM REQUIRESERVICELIST ";
		sql+=condition;
		PreparedStatement pstmt = null;
		ArrayList<RequestServiceList> arrRequestServiceListVO = new ArrayList<RequestServiceList>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RequestServiceList tempRequestServiceListVO = new RequestServiceList(rs.getString("RECEIPTID"),
						 rs.getString("CUSLICNUM"),
						 rs.getString("RENTALID"),
						 rs.getString("REQDATE"),
						 rs.getString("CUSPHONENO"));
				arrRequestServiceListVO.add(tempRequestServiceListVO);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return arrRequestServiceListVO;
	}
	
	public ArrayList<ServicesList> selectServicesList(String condition) {
		String sql = "SELECT * FROM SERVICESLIST ";
		sql+=condition;
		PreparedStatement pstmt = null;
		ArrayList<ServicesList> arrServicesListVO = new ArrayList<ServicesList>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ServicesList tempServicesListVO = new ServicesList(rs.getString("SERVICEID"),
						rs.getString("CAMPCARID"),
						 rs.getString("SERVCENTERID"),
						 rs.getString("RENTCOMPID"),
						 rs.getString("CUSLICNUM"),
						 rs.getString("SERVICEITEM"),
						 rs.getString("SERVDATE"),
						 rs.getInt("SERVBILL"),
						 rs.getString("BILLDATE"),
						 rs.getString("OTHERSERVITEM"));
				arrServicesListVO.add(tempServicesListVO);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return arrServicesListVO;
	}
	
	
	public ArrayList<DescribeTableVO> getTableDescription(String tableName) {
		String sql = "select COLUMN_NAME from COLS where table_name=?";
		PreparedStatement pstmt = null;
		ArrayList<DescribeTableVO> arrDescribeTableVO = new ArrayList<DescribeTableVO>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName.toUpperCase());
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.println("getTableDescription");
				System.out.println(rs.getString("COLUMN_NAME"));
				DescribeTableVO tempDescribeTableVO = new DescribeTableVO(rs.getString("COLUMN_NAME"));
				arrDescribeTableVO.add(tempDescribeTableVO);
			}
	
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return arrDescribeTableVO;
	}
	
	//고객 정보 입력
	public void insertCustomerInfo(String licnum, String cusname, String cusaddr, String cusphoneno, String cusemail, String prevdate, String prevcarid) {
		String sql = "INSERT INTO Customer VALUES (?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		String msg = "";
		int cnt = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, licnum);
			pstmt.setString(2, cusname);
			pstmt.setString(3, cusaddr);
			pstmt.setString(4, cusphoneno);
			pstmt.setString(5, cusemail);
			pstmt.setString(6, prevdate);
			pstmt.setString(7, prevcarid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			cnt++;
			msg += e.toString();
			
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(cnt > 0)
			JOptionPane.showMessageDialog(null, msg, "알림", JOptionPane.INFORMATION_MESSAGE);
	}
	
	//캠핑카 회사 정보 입력
	public void insertCompanyInfo(String compid, String name, String address, String phoneno, String manager, String email) {
		String sql = "INSERT INTO RentalCompany VALUES (?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		String msg = "";
		int cnt = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, compid);
			pstmt.setString(2, name);
			pstmt.setString(3, address);
			pstmt.setString(4, phoneno);
			pstmt.setString(5, manager);
			pstmt.setString(6, email);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			cnt++;
			msg += e.toString();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(cnt > 0)
			JOptionPane.showMessageDialog(null, msg, "알림", JOptionPane.INFORMATION_MESSAGE);
	}
	
	//정비소 정보 입력
	public void insertServiceCenterInfo(String centerid, String name, String address, String phoneno, String manager, String email) {
		String sql = "INSERT INTO CarServiceCenter VALUES (?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		String msg = "";
		int cnt = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, centerid);
			pstmt.setString(2, name);
			pstmt.setString(3, address);
			pstmt.setString(4, phoneno);
			pstmt.setString(5, manager);
			pstmt.setString(6, email);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			msg += e.toString();
			cnt++;
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(cnt > 0)
			JOptionPane.showMessageDialog(null, msg, "알림", JOptionPane.INFORMATION_MESSAGE);
	}
	
	//캠핑카 등록
	public void insertCampCarInfo(String carid, String compid, String name, String carnum, int passengers, String detail1, String detail2, int rentalfee, String regdate) {
		String sql = "INSERT INTO CAMPINGCAR VALUES (?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		String msg = "";
		int cnt = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, carid);
			pstmt.setString(2, compid);
			pstmt.setString(3, name);
			pstmt.setString(4, carnum);
			pstmt.setInt(5, passengers);
			pstmt.setString(6, detail1);
			pstmt.setString(7, detail2);
			pstmt.setInt(8, rentalfee);
			pstmt.setString(9, regdate);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			msg += e.toString();
			cnt++;
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(cnt > 0)
			JOptionPane.showMessageDialog(null, msg, "알림", JOptionPane.INFORMATION_MESSAGE);
	}
	
	//대여 정보 입력
	public void insertRentInfo(String rentid, String carid, String licnum, String compid, String startdate, int days, int bills, String billdate, String extra, int extrabill) {
		String sql = "INSERT INTO RentList VALUES (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		String msg = "";
		int cnt = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rentid);
			pstmt.setString(2, carid);
			pstmt.setString(3, licnum);
			pstmt.setString(4, compid);
			pstmt.setString(5, startdate);
			pstmt.setInt(6, days);
			pstmt.setInt(7, bills);
			pstmt.setString(8, billdate);
			pstmt.setString(9, extra);
			pstmt.setInt(10, extrabill);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			msg += e.toString();
			cnt++;
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(cnt > 0)
			JOptionPane.showMessageDialog(null, msg, "알림", JOptionPane.INFORMATION_MESSAGE);
	}
	
	//서비스 요청 목록 입력
	public void insertRequstsService(String recpid, String licnum, String rentid, String recpdate, String cusphoneno) {
		String sql = "INSERT INTO RequireServiceList VALUES (?,?,?,?,?)";
		PreparedStatement pstmt = null;
		String msg = "";
		int cnt = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, recpid);
			pstmt.setString(2, licnum);
			pstmt.setString(3, rentid);
			pstmt.setString(4, recpdate);
			pstmt.setString(5, cusphoneno);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			msg += e.toString();
			cnt++;
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(cnt > 0)
			JOptionPane.showMessageDialog(null, msg, "알림", JOptionPane.INFORMATION_MESSAGE);
	}
	
	//정비 정보 입력
	public void insertServiceInfo(String servid, String carid, String centerid, String compid, String licnum, String detail, String date, int bills, String billdate, String extra) {
		
		String sql = "INSERT INTO ServicesList VALUES (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		String msg = "";
		int cnt = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, servid);
			pstmt.setString(2, carid);
			pstmt.setString(3, centerid);
			pstmt.setString(4, compid);
			pstmt.setString(5, licnum);
			pstmt.setString(6, detail);
			pstmt.setString(7, date);
			pstmt.setInt(8, bills);
			pstmt.setString(9, billdate);
			pstmt.setString(10, extra);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			msg += e.toString();
			cnt++;
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(cnt > 0)
			JOptionPane.showMessageDialog(null, msg, "알림", JOptionPane.INFORMATION_MESSAGE);
	}
	//투플 삭제
	public void deleteTuple(String tableName, String tupleId, String colName) {
		String sql = "DELETE FROM "+ tableName+ " WHERE "+colName+"='"+tupleId+"'";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateCustomer(String condition, String licnum, String name, String address, String phoneno, String email, String prevdate, String prevcarid) {
		String sql = "UPDATE CUSTOMER SET LICNUM='"+licnum+"', CUSNAME='"+name+"', CUSADDRESS='"+address+"', CUSPHONE='"+phoneno+"', CUSEMAIL='"+email+"', LASTUSEDATE='"+prevdate+"', LASTCARID='"+prevcarid+"' WHERE LICNUM='"+condition+"'";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateCompanyInfo(String condition, String compid, String name, String address, String phoneno, String manager, String email) {
		String sql = "UPDATE RENTALCOMPANY SET COMPID='"+compid+"', COMPNAME='"+name+"', COMPADDRESS='"+address+"', COMPPHONENO='"+phoneno+"', COMPMANAGER='"+manager+"', COMPMANAGEREMAIL='"+email+"' WHERE COMPID='"+condition+"'";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateServiceCenterInfo(String condition, String centerid, String name, String address, String phoneno, String manager, String email) {
		String sql = "UPDATE CARSERVICECENTER SET CENTERID='"+centerid+"', CENTERNAME='"+name+"', CENTERADDRESS='"+address+"', CENTERPHONENO='"+phoneno+"', CENTERMANAGER='"+manager+"', CENTERMANAGEREMAIL='"+email+"' WHERE CENTERID='"+condition+"'";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateCampCarInfo(String condition, String carid, String compid, String nameA, String carnum, int passengers, String detail1, String detail2, int rentalfee, String regdate) {
		String sql = "UPDATE CAMPINGCAR SET CARID='"+carid+"', COMPANYID='"+compid+"', CARNAME='"+nameA+"', CARSNUM='"+carnum+"', NOPASSENGERS="+passengers+", CARDETAILS1='"+detail1+"', CARDETAILS2='"+detail2+"', RENTALFEE="+rentalfee+", REGDATE='"+regdate+"' WHERE CARID='"+condition+"'";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateRentInfo(String condition, String rentid, String carid, String licnum, String compid, String startdate, int days, int bills, String billdate, String extra, int extrabill) {
		String sql = "UPDATE RENTLIST SET RENTALID='"+rentid+"', CAMPCARID='"+carid+"', CUSLICNUM='"+licnum+"', RENTCOMPID='"+compid+"', STARTDATE="+startdate+", RENTALPERIOD="+days+", RENTALBILL="+bills+", BILLDATE='"+billdate+"', OTHERS='"+extra+"', EXTBILL="+extrabill+" WHERE RENTALID='"+condition+"'";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateRequstsService(String condition, String recpid, String licnum, String rentid, String recpdate, String cusphoneno) {
		String sql = "UPDATE REQUIRESERVICELIST SET RECEIPTID='"+recpid+"', CUSLICNUM='"+licnum+"', RENTALID='"+rentid+"', REQDATE='"+recpdate+"', CUSPHONENO='"+cusphoneno+"' WHERE RECEIPTID='"+condition+"'";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateServiceInfo(String condition, String servid, String carid, String centerid, String compid, String licnum, String detail, String date, int bills, String billdate, String extra) {
		String sql = "UPDATE SERVICESLIST SET SERVICEID='"+servid+"', CAMPCARID='"+carid+"', SERVCENTERID='"+centerid+"', RENTCOMPID='"+compid+"', CUSLICNUM='"+licnum+"', SERVICEITEM='"+detail+"', SERVDATE='"+date+"', SERVBILL="+bills+", BILLDATE='"+billdate+"', OTHERSERVITEM='"+extra+"' WHERE SERVICEID='"+condition+"'";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

class DescribeTableVO {
	private String column_name;

	public DescribeTableVO(String column_name) {
		super();
		this.column_name = column_name;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	@Override
	public String toString() {
		return "DescribeTableVO [column_name=" + column_name + "]";
	}

}

class RentalCompany {
	private String compID;
	private String compName;
	private String compAddress;
	private String compPhoneNo;
	private String compManager;
	private String compManagerEmail;
	
	
	public RentalCompany() {
		
	}
	public RentalCompany(String compID, String compName, String compAddress, String compPhoneNo, String compManager, String compManagerEmail) {
		super();
		this.compID = compID;
		this.compName = compName;
		this.compAddress = compAddress;
		this.compPhoneNo = compPhoneNo;
		this.compManager = compManager;
		this.compManagerEmail = compManagerEmail;
}
	public String getCompID() {
		return compID;
	}

	public void setCompID(String compID) {
		this.compID = compID;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCompAddress() {
		return compAddress;
	}

	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress;
	}

	public String getCompPhoneNo() {
		return compPhoneNo;
	}

	public void setCompPhoneNo(String compPhoneNo) {
		this.compPhoneNo = compPhoneNo;
	}


	public String getCompManager() {
		return compManager;
	}

	public void setCompManager(String compManager) {
		this.compManager = compManager;
	}
	
	public String getCompManagerEmail() {
		return compManagerEmail;
	}

	public void setCompManagerEmail(String compManagerEmail) {
		this.compManagerEmail = compManagerEmail;
	}


	@Override
	public String toString() {
		return "RentalCompany [compID=" + compID + ", compName=" + compName + ", compAddress=" + compAddress +
				", compPhoneNo=" + compPhoneNo+", compManager="+ compManager +", compManagerEmail ="+compManagerEmail+ "]";
	}
}

class RentList {
	private String rentalID;
	private String campCarID;
	private String cusLicNum;
	private String rentCompID;
	private String startDate;
	private int rentalPeriod;
	private int rentalBill;
	private String billDate;
	private String others;
	private int extBill;
	
	public RentList() {
	}

	public RentList(String rentalID, String campCarID, String cusLicNum,String rentCompID,String startDate,
			int rentalPeriod, int rentalBill,String billDate,String others, int extBill) {
		super();
		this.rentalID = rentalID;
		this.campCarID = campCarID;
		this.cusLicNum = cusLicNum;
		this.rentCompID = rentCompID;
		this.startDate=startDate;
		this.rentalPeriod=rentalPeriod;
		this.rentalBill=rentalBill;
		this.billDate=billDate;
		this.others=others;
		this.extBill=extBill;
	}

	public String getRentalID() {
		return rentalID;
	}

	public void setRentalID(String rentalID) {
		this.rentalID = rentalID;
	}

	public String getCampCarID() {
		return campCarID;
	}

	public void setCampCarID(String campCarID) {
		this.campCarID = campCarID;
	}

	public String getCusLicNum() {
		return cusLicNum;
	}

	public void setCusLicNum(String cusLicNum) {
		this.cusLicNum = cusLicNum;
	}
	
	public String getRentCompID() {
		return rentCompID;
	}

	public void setRentCompID(String rentCompID) {
		this.rentCompID = rentCompID;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getRentalPeriod() {
		return rentalPeriod;
	}

	public void setRentalPeriod(int rentalPeriod) {
		this.rentalPeriod = rentalPeriod;
	}
	
	public int getRentalBill() {
		return rentalBill;
	}

	public void setRentalBill(int rentalBill) {
		this.rentalBill = rentalBill;
	}
	
	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}
	
	public int getExtBill() {
		return extBill;
	}

	public void setExtBill(int extBill) {
		this.extBill = extBill;
	}

	@Override
	public String toString() {
		return "RentList [rentalID=" + rentalID + ", campCarID=" + campCarID + ", cusLicNum=" + cusLicNum + 
				", rentCompID="+ rentCompID + ", startDate=" + startDate + ", rentalPeriod=" + rentalPeriod +
				", rentalBill=" + rentalBill + ", billDate=" + billDate + ", others=" + others+", extBill=" + extBill+"]";
	}
}

class RequestServiceList {
	private String receiptID;
	private String cusLicNum;
	private String rentalID;
	private String date;
	private String cusPhoneNo;
	public RequestServiceList() {
	}
	
	public RequestServiceList(String receiptID, String cusLicNum, String rentalID, String  date, String cusPhoneNo) {
		super();
		this.receiptID = receiptID;
		this.cusLicNum = cusLicNum;
		this.rentalID = rentalID;
		this.date =  date;
		this.cusPhoneNo = cusPhoneNo;
	}
	public String getReceiptID() {
		return receiptID;
	}

	public void setReceiptID(String receiptID) {
		this.receiptID = receiptID;
	}

	public String getCusLicNum() {
		return cusLicNum;
	}

	public void setCusLicNum(String cusLicNum) {
		this.cusLicNum = cusLicNum;
	}

	public String getRentalID() {
		return rentalID;
	}

	public void setRentalID(String rentalID) {
		this.rentalID = rentalID;
	}

	public String getDate() {
		return  date;
	}

	public void setDate(String  date) {
		this. date =  date;
	}

	public String getCusPhoneNo() {
		return cusPhoneNo;
	}

	public void setCusPhoneNo(String cusPhoneNo) {
		this.cusPhoneNo = cusPhoneNo;
	}

	
	@Override
	public String toString() {
		return "RequireServiceList [receiptID=" + receiptID + ", cusLicNum=" + cusLicNum + ", rentalID=" + rentalID +
				", date=" +  date + ", cusPhoneNo=" + cusPhoneNo + "]";
	}
}

class ServicesList {
	private String serviceID;
	private String campCarID;
	private String servCenterID;
	private String rentCompID;
	private String cusLicNum;
	private String serviceItem;
	private String servDate;
	private int servBill;
	private String billDate;
	private String otherServItem;
	
	public ServicesList() {
	}
	
	public ServicesList(String serviceID, String campCarID, String servCenterID, String rentCompID, String cusLicNum, String serviceItem, String servDate, 
			int servBill, String billDate, String otherServItem) {
		super();
		this.serviceID = serviceID;
		this.campCarID = campCarID;
		this.servCenterID = servCenterID;
		this.rentCompID =  rentCompID;
		this.cusLicNum = cusLicNum;
		this.serviceItem = serviceItem;
		this.servDate = servDate;
		this.servBill = servBill;
		this.billDate = billDate;
		this.otherServItem = otherServItem;
	}
	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getCampCarID() {
		return campCarID;
	}

	public void setCampCarID(String campCarID) {
		this.campCarID = campCarID;
	}

	public String getServCenterID() {
		return servCenterID;
	}

	public void setServCenterID(String servCenterID) {
		this.servCenterID = servCenterID;
	}

	public String getRentCompID() {
		return  rentCompID;
	}

	public void setRentCompID(String  rentCompID) {
		this. rentCompID =  rentCompID;
	}

	public String getCusLicNum() {
		return cusLicNum;
	}

	public void setCusLicNum(String cusLicNum) {
		this.cusLicNum = cusLicNum;
	}
	
	public String getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(String serviceItem) {
		this.serviceItem = serviceItem;
	}
	
	public String getServDate() {
		return servDate;
	}

	public void setServDate(String servDate) {
		this.servDate = servDate;
	}
	public int getServBill() {
		return servBill;
	}

	public void setServBill(int servBill) {
		this.servBill = servBill;
	}
	
	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	
	public String getOtherServItem() {
		return otherServItem;
	}

	public void setOtherServItem(String otherServItem) {
		this.otherServItem = otherServItem;
	}

	
	@Override
	public String toString() {
		return "ServicesList [serviceID=" + serviceID + ", campCarID=" + campCarID + ", servCenterID=" + servCenterID +
				", rentCompID=" +  rentCompID + ", cusLicNum=" + cusLicNum +", serviceItem="+ serviceItem + 
				", servDate=" +  servDate +", servBill=" +  servBill +", billDate=" +  billDate +
				", otherServItem=" +  otherServItem +"]";
	}
}


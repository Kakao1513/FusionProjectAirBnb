package Container;

import view.*;

public class ViewContainer {

	private static final ViewContainer viewContainer = new ViewContainer();
	private static final UserView userView = new UserView();
	private static final AccommodationView acView = new AccommodationView();
	private static final AmenityView amenityView = new AmenityView();

	private static final ReviewView reviewView = new ReviewView();

	private static final ReservationView reservationView = new ReservationView();

	private ViewContainer() {
	}

	public static ViewContainer getInstance() {
		return viewContainer;
	}

	public ReviewView reviewView() {
		return reviewView;
	}

	public AccommodationView accommodationView() {
		return acView;
	}

	public UserView userView() {
		return userView;
	}

	public AmenityView amenityView() {
		return amenityView;
	}

	public ReservationView reservationView() {
		return reservationView;
	}
}

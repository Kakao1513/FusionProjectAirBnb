package Container;

import view.AccommodationView;
import view.AmenityView;
import view.UserView;

public class ViewContainer {

	private static final ViewContainer viewContainer = new ViewContainer();
	private static final UserView userView = new UserView();
	private static final AccommodationView acView = new AccommodationView();
	private static final AmenityView amenityView = new AmenityView();

	private ViewContainer() {
	}

	public static ViewContainer getInstance() {
		return viewContainer;
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
}

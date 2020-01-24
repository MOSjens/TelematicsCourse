package dbconnection;

public enum Category {

	GENERAL_KNOWLEDGE(9,"General Knowledge"), ENTERTAINMENT_BOOKS(10,"Entertainment Books"), 
	ENTERTAINMENT_FILM(11,"Entertainment Film"), ENTERTAINMENT_MUSIC(12,"Entertainment Music"), 
	ENTERTAINMENT_MUSICALS_THEATRES(13,"Entertainment Musicals & Theatres"),ENTERTAINMENT_TELEVISION(14,"Entertainment Television"),
	ENTERTAINMENT_VIDEOGAMES(15,"Entertainment Video Games"),ENTERTAINMENT_BOARDGAMES(16,"Entertainment Board Games"),
	SCIENCE_NATURE(17,"Science & Nature"), SCIENCE_COMPUTERS(18,"Science Computers"),
	SCIENCE_MATHEMATICS(19,"Science Mathematics"), MYTHOLOGY(20,"Mythology"),
	SPORTS(21,"Sports"),GEOGRAPHY(22,"Geography"),HISTORY(23,"History"),
	POLITICS(24,"Politics"), ART(25,"Art"),CELEBRITIES(26,"Celebrities"),
	ANIMALS(27,"Animals"),VEHICLES(28,"Vehicles"),ENTERTAINMENT_COMICS(29,"Entertainment Comics"),
	SCIENCE_GADGETS(30,"Science Gadgets"),ENTERTAINMENT_MANGA(31,"Entertainment Japanese Anime & Manga"),
	ENTERTAINMENT_CARTOON(32,"Entertainment Cartoon & Animations");
	
	private int categoryId;
	private String categoryName;
	
	Category(int id, String name){
		this.categoryId = id;
		this.categoryName = name;
	}

	public int getCategoryId() {
		return categoryId;
	}

	@Override
	public String toString() {
		return categoryName;
	}
}

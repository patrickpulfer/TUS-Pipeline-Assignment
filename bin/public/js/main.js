

var rootUrl = "http://localhost:8081/cars"
var list;

// Step 3
var findAll = function () {
	$.ajax({
		type: 'GET',
		url: rootUrl,
		dataType: "json",
		success: renderList
	});
}

//Step 4
var findById = function (id) {
	$.ajax({
		type: 'GET',
		url: rootUrl + "/" + id,
		dataType: "json",
		success: function (data) {
			currentEntry = data;
			showDetails(currentEntry);
		}
	});
}

//Step 5
var findByDates = function (startDate, endDate) {
	$.ajax({
		type: 'GET',
		url: rootUrl + '/search/year/' + startDate + '/' + endDate,
		dataType: "json",
		success: function (data) {
			$('#whichCars').text("Filtering from Year: " + startDate + ' to ' + endDate);
			renderList(data);
		}
	});
}

var findByPrice = function (lowerPrice, upperPrice) {
	$.ajax({
		type: 'GET',
		url: rootUrl + '/price/' + lowerPrice + '/' + upperPrice,
		dataType: "json",
		success: function (data) {
			$('#whichCars').text("Filtering by Price Range: " + lowerPrice + ' to ' + upperPrice);
			renderList(data);
		}
	});
}

var findByMake = function (make) {
	$.ajax({
		type: 'GET',
		url: rootUrl + '/car/' + make,
		dataType: "json",
		success: function (data) {
			$('#whichCars').text("Filtering by Make");
			renderList(data);
		}
	});
}

var renderList = function (list) {
	htmlStr = '';
	$('.scroll_cars').remove();
	$.each(list, function (index, cars) {
		htmlStr += '<div class="scroll_cars">';
		htmlStr += '<img src="images/cars/' + cars.image + '" alt="Car">';
		htmlStr += '<p>' + cars.year + ' ' + cars.make + ' ' + cars.model + '</p>';
		htmlStr += '<a href id="' + cars.id + '">';
		htmlStr += '<span class="fa fa-info-circle fa-2x" id="' + cars.id + ' "></span></a></div>';
	});
	$('.list_cars_scroll').append(htmlStr);
};

var showDetails = function (car) {
	$('#detailsModal').find('.modal-title').text("Details for " + car.year + ' ' + car.make + ' ' + car.model);
	$('#pic').attr('src', 'images/cars/' + car.image);
	$('#color').val(car.color);
	$('#year').val(car.year);
	$('#engine').val(car.litre + ' litres');
	$('#miles').val(car.mileage + ' miles');
	$('#condition').val(car.cond);
	$('#price').val("€"+car.price);
	$('#detailsModal').show();
}


$(document).ready(function () {
	// Event handler for the modal info
	$(document).on("click", ".fa-info-circle", function () {
		findById(this.id);
		return false;
	});

	// Event handler for the modal filter by year
	$("#searchYearButton").click(function () {
		$('#filterYearModal').modal('show');
		return false;
	});

	// Event handler for the modal submit search year button
	$(document).on("click", "#submitSearchYear", function () {
		console.log("Searcing between years " + $('#minYear').val() + " and " + $('#maxYear').val());
		$('#filterYearModal').modal('hide');
		findByDates($('#minYear').val(), $('#maxYear').val());
		return false;
	});



	// Event handler for the modal filter by make
	$("#searchMakeButton").click(function () {
		$('#filterMakeModal').modal('show');
		return false;
	});
	// Event handler for the modal submit search make button
	$(document).on("click", "#submitSearchMake", function () {
		console.log("Searcing for make  " + $('#searchbyMake').val());
		$('#filterMakeModal').modal('hide');
		findByMake($('#searchbyMake').val());
		return false;
	});


	// Event handler for the modal filter by price
	$("#searchPriceButton").click(function () {
		$('#filterPriceModal').modal('show');
		return false;
	});
	// Event handler for the modal submit search Price button
	$(document).on("click", "#submitSearchPrice", function () {
		console.log("Searcing between years " + $('#minPrice').val() + " and " + $('#maxPrice').val());
		$('#filterPriceModal').modal('hide');
		findByPrice($('#minPrice').val(), $('#maxPrice').val());
		return false;
	});

	$(document).on('click', '#listAllButton', function () {
		console.log("Displaying all cars");
		findAll();
		$('#whichCars').text("Dispaying all cars");
		return false;
	});

	$("#closeXyear").click(function () {
		$('#filterYearModal').modal('hide');
		return false;
	});
	$("#closeXmake").click(function () {
		$('#filterMakeModal').modal('hide');
		return false;
	});
	$("#closeXprice").click(function () {
		$('#filterPriceModal').modal('hide');
		return false;
	});

	findAll();
});
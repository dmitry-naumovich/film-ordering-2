window.setTimeout(function() {
	    $(".alert").fadeTo(500, 0).slideUp(500, function(){
	        $(this).remove(); 
	    });
}, 2000);
	
$('.nav li').click(function(e) {
  e.preventDefault();
  $('.nav li').removeClass('active');
  $(this).addClass('active');
});

document.getElementByClass("disabledBtn").disabled = true;

$( function() {
	$( ".datepicker" ).datepicker();
} );
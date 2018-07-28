$(document).ready(function() {
        $('.onlydouble').keypress(function (event) {
            return isDouble(event, this)
        });
    });
    
    function isDouble(evt, element) {

        var charCode = (evt.which) ? evt.which : event.keyCode

        if ((charCode != 46 || $(element).val().indexOf('.') != -1) &&
            (charCode < 48 || charCode > 57))
            return false;

        return true;
    }
    
    
    
    $(document).ready(function() {
        $('.onlyint').keypress(function (event) {
            return isNumber(event, this)
        });
    });
    
    function isNumber(evt, element) {

        var charCode = (evt.which) ? evt.which : event.keyCode

        if ((charCode != 46 || $(element).val().indexOf('.') == -1) && 
        		(charCode < 48 || charCode > 57) && (charCode != 45 || $(element).val().indexOf('-') != -1))
            return false;

        return true;
    }
    
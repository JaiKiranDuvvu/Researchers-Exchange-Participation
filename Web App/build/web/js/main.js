$(document).ready(function(){
    $('#login_button').on('click',function(e){
       e.preventDefault();
       var email=$('#email').val().trim();
       var password=$('#password').val().trim();
       if(!isEmail(email)){
           $('#email').focus();
           $('#email').addClass('error');
           return false;
       }else if(password===''){
           $('#password').focus();
           $('#password').addClass('error');
           return false;
       }
       else{
           $('.msgOverlay').html('Authenticating<br/>Please Wait......');
           $("#overlay").css('z-index' , '9500').animate( { "opacity" : .5 }, 250);
           AesEncrypt(password,false);
           $('#login_form').submit();
           $("#overlay").fadeOut('slow');
           return false;
       }
    });
    $('#signup_page_button').on('click',function(e){
       e.preventDefault();
       var name=$('#name').val().trim();
       var email=$('#email').val().trim();
       var password=$('#password').val().trim();
       var cpassword=$('#cpassword').val().trim();
       if(name === ''){
           $('#name').focus();
           $('#name').addClass('error');
           return false;
       }else if(!isEmail(email)){
           $('#email').focus();
           $('#email').addClass('error');
           return false;
       }else if(password === ''){
           $('#password').focus();
           $('#password').addClass('error');
           return false;
       }else if(cpassword === ''){
           $('#cpassword').focus();
           $('#cpassword').addClass('error');
           return false;
       }else if(password !== cpassword){
           $('#cpassword').focus();
           $('#cpassword').addClass('error');
           alert('Passwords does not match');
           return false;
       }
       else{
           $('.msgOverlay').html('Registration in Progress<br/>Please Wait......');
           $("#overlay").css('z-index' , '9500').animate( { "opacity" : .5 }, 250);
           AesEncrypt(password,true);
           $('#signup_page_form').submit();
           $("#overlay").fadeOut('slow');
           return false;
       }
    });
    
    $('#test_button').on('click',function(e){
    	e.preventDefault();
        var email1=$('#email').val().trim();
        var password1=$('#password').val().trim();
        var cpassword1=$('#cpassword').val().trim();
        if(!isEmail(email1)){
            $('#email').focus();
            $('#email').addClass('error');
            return false;
        }else if(password1 === ''){
            $('#password').focus();
            $('#password').addClass('error');
            return false;
        }else if(cpassword1 === ''){
            $('#cpassword').focus();
            $('#cpassword').addClass('error');
            return false;
        }else if(password1 !== cpassword1){
            $('#cpassword').focus();
            $('#cpassword').addClass('error');
            alert('Passwords does not match');
            return false;
        }
        else{
            $('.msgOverlay').html('Updating password <br/>Please Wait......');
            $("#overlay").css('z-index' , '9500').animate( { "opacity" : .5 }, 250);
            AesEncrypt(password1,true);
            $('#change_password_form').submit();
            $("#overlay").fadeOut('slow');
            return false;
        }
     });
    
    
    function isEmail(email){
        return /^([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22))*\x40([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d))*$/.test( email );
    }
    $('#browse_button').on('click',function(){
       $('#browse_file').click();
    });
    $('#browse_file').on('change',function(){
       $('#imageNameholder').text($('#browse_file').val());
    });
    $('#upload_button').on('click',function(e){
       e.preventDefault();
       $('input').removeClass('error');
       if($('#study_name').val()===''){
           $('#study_name').focus();
           $('#study_name').addClass('error');
           return false;
       }else if($('#question_text').val()===''){
           $('#question_text').focus();
           $('#question_text').addClass('error');
           return false;
       }else if($('#browse_file').val()===''){
           $('#browse_button').focus();
           return false;
       }else if($('#participants').val()===''){
           $('#participants').focus();
           $('#participants').addClass('error');
           return false;
       }else if($('#study_answers').val()===''){
           $('#study_answers').focus();
           return false;
       }else if($('#study_answers').val()>0){
           var noOfAnswers=$('#study_answers').val();
           for(var i=1;i<=noOfAnswers;i++){
               if($('#Answer'+i).val()===''){
                   $('#Answer'+i).focus();
                   $('#Answer'+i).addClass('error');
                   break;
                   return false;
               }
           }
           if($('#description').val()===''){
            $('#description').focus();
            $('#description').addClass('error');
            return false;
           }else{
            $('#studies_form').submit();
           }
       }
    });
    $('#contact_button').on('click',function(e){
       e.preventDefault();
       $('input').removeClass('error');
       if($('#study_name').val()===''){
           $('#study_name').focus();
            $('#study_name').addClass('error');
       }else if($('#email').val()===''){
           $('#email').focus();
            $('#email').addClass('error');
       }else if($('#message').val()===''){
           $('#message').focus();
            $('#message').addClass('error');
       }else{
            $('#contact_form').submit();
       }  
    });
    $('#recommend_form_button').on('click',function(e){
       e.preventDefault();
       $('input').removeClass('error');
       if($('#study_name').val()===''){
           $('#study_name').focus();
            $('#study_name').addClass('error');
       }else if($('#email').val()===''){
           $('#email').focus();
            $('#email').addClass('error');
       }else if($('#friend_email').val()===''){
           $('#friend_email').focus();
            $('#friend_email').addClass('error');
       }else if($('#message').val()===''){
           $('#message').focus();
            $('#message').addClass('error');
       }else{
            $('#recommend_form_id').submit();
       }  
    });
});
function AesEncrypt(password,isSignUp)
{
		var key = CryptoJS.enc.Latin1.parse('0123456789abcdef');
		var iv  = CryptoJS.enc.Latin1.parse('fedcba9876543210');
	  	var ecryptedText = CryptoJS.AES.encrypt(padString(password), key, { iv: iv, padding: CryptoJS.pad.NoPadding, mode: CryptoJS.mode.CBC});
		$('#password').val(ecryptedText);
                if(isSignUp)
                    $('#cpassword').val(ecryptedText);
                console.log('Original Password::'+password);
                console.log('Encrypted Password::'+ecryptedText);	  	  
}
function padString(source) {
    var paddingChar = ' ';
    var size = 16;
    var x = source.length % size;
    var padLength = size - x;
    
    for (var i = 0; i < padLength; i++) source += paddingChar;
    
    return source;
}



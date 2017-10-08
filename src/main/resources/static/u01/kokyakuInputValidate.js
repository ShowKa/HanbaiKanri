(function(){
  //標準エラーメッセージの変更
  $.extend($.validator.messages, {
    required: '*入力必須です',

  });
  //セレクトボックスの判定を追加
  jQuery.validator.addMethod('selectCheck', function (value) {
      return (value != '');
  }, "*選択必須です");

　//追加ルールの定義
  var methods = {
	alphanumeric: function(value, element){
	    return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
	  },
	date: function(value, element){
	    return this.optional(element) || /^[1-9]$|^[1-2][0-9]$|^30$/.test(value);
	  },
  };

  //メソッドの追加
  $.each(methods, function(key) {
    $.validator.addMethod(key, this);
  });

  //入力項目の検証ルール定義
  var rules = {
	code: {required: true, maxlength: 4, alphanumeric: true},
	name: {required: true, maxlength: 255},
	address: {required: true, maxlength: 255},
	nyukinDate: {date: true},

  };

  //入力項目ごとのエラーメッセージ定義
  var messages = {
    code: {
    	maxlength: "*4文字以下で入力してください",
   		alphanumeric: "*半角英数字で入力してください"
    },
    name: {
    	maxlength: "*255文字以下で入力してください"
    },
    address: {
    	maxlength: "*255文字以下で入力してください"
    },
    nyukinDate: {
    	date: "*1-30を入力してください"
    },
  };




  $(function(){
    $('#registerForm').validate({
      rules: rules,
      messages: messages,

      //エラーメッセージ出力箇所調整
      errorPlacement: function(error, element){
        if (element.is(':radio')) {
          error.appendTo(element.parent());
        }else {
          error.insertAfter(element);
        }
      }
    });
  });

})();
var CryptoUtils = {
	encrypt : function(password) {
		var iv = CryptoJS.lib.WordArray.random(128 / 8).toString(
				CryptoJS.enc.Hex);
		var salt = CryptoJS.lib.WordArray.random(128 / 8).toString(
				CryptoJS.enc.Hex);
		var keySize = 128;
		var iterationCount = 2000;
		var passPhrase = "konantech";

		// PBKDF2 키 생성
		var key128Bits100Iterations = CryptoJS.PBKDF2(passPhrase,
				CryptoJS.enc.Hex.parse(salt), {
					keySize : keySize / 32,
					iterations : iterationCount
				});

		var encrypted = CryptoJS.AES.encrypt(password, key128Bits100Iterations,
				{
					iv : CryptoJS.enc.Hex.parse(iv)
				});

		var map = {
			iv : iv,
			salt : salt,
			encPassword : encrypted.toString()
		};

		return map;
	}
};
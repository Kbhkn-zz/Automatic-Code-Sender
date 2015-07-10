Automatic Code Sender
===================

>***Projedeki Amaç:***
>
> *** YKB Stajında verilen bir projedir**

> 
 * Application Sunucularında ani bir durum çıktığında birden fazla sunucuda bulunan, logları vs. incelemek gerekiyor. Bu masaüstü uygulamasıda birden fazla sunucuya eş zamanlı komutları göndermeyi ve sonuçlarını ekrana hızlı bir biçimde ve düzenli bir şekilde dökme işlemine yarıyor.
 * Socket -- Process -- Thread mantığını bildiğim kadarıyla projeyi tasarladım.
 *  Eş zamanlı birden fazla user aynı sunucuya komut gönderildiği zaman çakışma meydana gelmemektedir.
 * Eş zamanlı tüm sunuculara komut gönderildiği zaman sırayla komut tüm sunuculara gönderilip işlenip her biri ayrı ayrı ekrana dökülüyor.
 * Programda BUG çıkmaması için işlem yapılırken bazıları kısıtlanıyor bazıları aktifleştiriliyor
 * Örneğin: Program ilk açıldığında komut listesi boştur sunucudan komut listesinin alınması zorunludur.
 * Herhangi bir komut / Server seçmeden kod gönderilemez.
 * Otomatik kod gönderme aktif olmadığı sürece Stop Timer aktif değildir.
 * Otomatik kod gönderme aktif olduğu zaman sadece Dİsconnect ve Stop Timer butonları aktiftir.
 * Tüm Editable özellikleri kapalıdır.
 * Elimden geldiğince kodu temiz sade anlaşılır ve kod tekrarı yapmamaya çalıştım.

1: Program ilk açıldığında: 

![Program ilk çalıştırıldığı an](https://raw.githubusercontent.com/Kbhkn/Automatic-Code-Sender/master/resim/1.png)

2: Komutlar sunucudan alınıp komut gönderildiği anda:

![Update sonrası](https://raw.githubusercontent.com/Kbhkn/Automatic-Code-Sender/master/resim/2.png) 

3: Otomatik kod gönderme aktifleştirildiği anda:

![Auto sonrası](https://raw.githubusercontent.com/Kbhkn/Automatic-Code-Sender/master/resim/3.png)

4: Loglama yapısı: 

![Log yapısı](https://raw.githubusercontent.com/Kbhkn/Automatic-Code-Sender/master/resim/4.png)


 * Evet arkadaşlar projenin User Interface kısmı aşağı yukarı bu kadardır indirip kullandıktan sonra dahada çok şey olduğunu göreceksinizdir.
 * Kodlardaki geliştirmelere açığım eğer daha performanslı daha kısa daha kullanışlı birşeyler keşfederseniz göndermenizden mutluluk duyarım.
 * Kendinize iyi bakın esen kalın :)

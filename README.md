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

![Program ilk çalıştırıldığı an](https://drive.google.com/file/d/0B333Pihz-VhzVWtoYUh1Qk1CaXc/view?usp=sharing)

2: Komutlar sunucudan alınıp komut gönderildiği anda:

![enter image description here](https://lh6.googleusercontent.com/y8GVJNRw7nA2KRUJD63kLwi6gB0CfiBRTXCyGe6RBcjoisxKe9Cyqm7C7oQLnLBPhfOzBi9z=w1574-h653) 

3: Otomatik kod gönderme aktifleştirildiği anda:

![enter image description here](https://lh3.googleusercontent.com/di5QJI7EVFfdNXE_rS97YATgH5B6C0-Xz2CPoXphMkrFyC7kqdOLl-zHRwAA8NlfC-FwDC1g=w1574-h653)

4: Loglama yapısı: 

![enter image description here](https://lh3.googleusercontent.com/o7yODdeckmFNNBEJmDlMc7UR7P3bMY6WkcxwGS5j3MJ0gocwiY2RA45KVhrHjq5aZHI6CLJS=w1574-h653)


Evet arkadaşlar projenin User Interface kısmı aşağı yukarı bu kadardır indirip kullandıktan sonra dahada çok şey olduğunu göreceksinizdir.

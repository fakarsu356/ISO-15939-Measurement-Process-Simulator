# ISO 15939 Measurement Process Simulator

1. Mimari Yapı :
Uygulama, CardLayout kullanılarak tasarlanmış tek bir pencere (JFrame) üzerinden yönetilir. 5 ana adım (Profile, Define, Plan, Collect, Analyse) ayrı paneller olarak tanımlanmış ve butonlar aracılığıyla geçiş sağlanmıştır.
Step Indicator: En üstte yer alan JLabel, HTML formatı kullanılarak dinamik olarak güncellenir; aktif adımı vurgular ve tamamlanan adımlara "✓" ekler.
2. Adım Mantığı ve Validasyon:
Step 1 (Profile): Kullanıcı verileri alınır. handleNext metodu içinde boş alan kontrolü yapılarak hata mesajı (JOptionPane) gösterilir.
Step 2 (Define): Mod (Education/Health) seçimine göre senaryolar updateScenarios metoduyla dinamik olarak yüklenir. Radio butonları ButtonGroup ile tekli seçime zorlanır.
3. Veri ve Hesaplama Mantığı:
Step 3 & 4 (Plan & Collect): Seçilen senaryoya göre veriler loadStep3Data ve loadStep4Data metodlarıyla DefaultTableModel üzerine işlenir.
Skor Formülü: calculateScore metodu, PDF'teki "Higher is better" ve "Lower is better" formüllerini uygular. Sonuçlar Math.round(s * 2) / 2.0 ile en yakın 0.5 değerine yuvarlanır.
4. Analiz ve Sonuç (Step 5):
Weighted Average: Metrik skorlarının ağırlıklı ortalaması hesaplanır ve sonuç 1-5 aralığından 0-100 yüzdesine çevrilerek JProgressBar üzerinde gösterilir.
Gap Analysis: Hedef puan (5.0) ile mevcut puan arasındaki fark (Gap) hesaplanır ve puan eşiklerine göre (Excellent, Good, Poor) dinamik geri bildirim verilir.
5. Teknik Detaylar:
Dil: Java (SE 17+)
Kütüphane: Java Swing & AWT
Düzen: BorderLayout (Ana), GridLayout ve FlowLayout (Paneller).

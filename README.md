# AdvancedJobScheduler
Kaynaklara iş atama simülasyonunun gelişmiş versiyonudur.
Gelen işler her okuma zamanı geliş zamanı sayılarak .txt dosyasından okunur. 
Ayrıca bağımlılıklar olarak tutulan .txt dosyası anında okunur ve işlerin bu bağımlılıkları geçene kadar kaynaklara atamaları yapılmaz.

It is an advanced version of job assignment simulation to resources.
Incoming jobs are read from the .txt file by counting the arrival time at each read time.
The .txt file kept as dependencies is read instantly and jobs are not assigned to resources until these dependencies are passed.

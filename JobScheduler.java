import java.io.*;
import java.util.*;

public class JobScheduler {
	public class MinHeap {
		// MinHeap dosyada oldugu gibi inner class olarak yazilmistir.
		public ArrayList<Integer> Heap = new ArrayList<>();
		public int elemanSayi;

		public MinHeap() {
			this.elemanSayi = 0;
			for (int i = 0; i < 20; i++) {
				Heap.add(null);
				// Burada initial olarak size'da kullanabilmek icin rastgele bir 20 elemanlik
				// dolum yapilmistir, asagida tum null kontrolleri yapilmaktadir ve gerektigi
				// takdirde resize eden metod tanimlanmistir.

			}
		}

		public void resize() {

			// Olasi bir heap'te kullanilan array'in dolmasi durumunda heap size'inin 2 kati
			// olan yeni bir arrayList olusturulur ve eski elemanlar buna set edilir ve
			// devamindaki elemanlar yine size problem cikarmamasi adina null olarak
			// eklenir.

			ArrayList<Integer> newHeap = new ArrayList<>();
			for (int i = 0; i < Heap.size(); i++) {
				newHeap.add(Heap.get(i));
			}
			for (int i = Heap.size(); i < Heap.size() * 2; i++) {
				newHeap.add(null);
			}

			// En son islem olarak, kullanilan heap'in adresi bu yeni olusturulmus
			// arrayList'e esitlenir ve size'i artmistir.
			Heap = newHeap;

		}

		public int parent(int i) {
			// Tek-cift sayisi kusurati etkilemektedir fakat int deger donecegi icin sorun
			// ortadan kalkar.
			return (i - 1) / 2;
		}

		public int leftChild(int i) {
			// Tek-cift sayisi kusurati etkilemektedir fakat int deger donecegi icin sorun
			// ortadan kalkar.
			return (i * 2) + 1;
		}

		public int rightChild(int i) {
			// Tek-cift sayisi kusurati etkilemektedir fakat int deger donecegi icin sorun
			// ortadan kalkar.
			return (i * 2) + 2;
		}

		public boolean isLeaf(int i) {
			if (rightChild(i) >= Heap.size() || leftChild(i) >= Heap.size()) {
				// Sag veya sol cocuk sayisi size'a esit veya daha fazla ise, leaf demektir
				// burada true return edilir.
				return true;
			}
			return false;
		}

		public void insert(int element) {
			if (!(elemanSayi >= Heap.size())) {
				// Size'dan buyuk olursa insert edilmemelidir o yuzden ustteki kontrol
				// yapilmistir.
				Heap.set(elemanSayi, element);
				int swaplenecekIndex = elemanSayi;
				// Minheap'teki ust basamakta ortanca degerin kalmasi icin gerekli olan swap
				// asagidadir
				while (Heap.get(swaplenecekIndex) < Heap.get(parent(swaplenecekIndex))) {
					// Insert edilen element parent'tan kucuk olana kadar swap'ler.
					swap(swaplenecekIndex, parent(swaplenecekIndex));
					swaplenecekIndex = parent(swaplenecekIndex);
				}
				elemanSayi++;
			} else {
				resize();
				// resize edilerek array size'i 2 katina cikarilmistir ve asagidaki kisim
				// ustteki if kosuluyla birebirdir.

				Heap.set(elemanSayi, element);
				int swaplenecekIndex = elemanSayi;
				// Minheap'teki ust basamakta ortanca degerin kalmasi icin gerekli olan swap
				// asagidadir
				while (Heap.get(swaplenecekIndex) < Heap.get(parent(swaplenecekIndex))) {
					swap(swaplenecekIndex, parent(swaplenecekIndex));
					swaplenecekIndex = parent(swaplenecekIndex);
				}
				elemanSayi++;
			}

		}

		public void remove() {
			// Heap'teki remove min calisir.
			if (Heap.size() > 0) {
				// Bu kontrolun yapilmasi onemlidir, aksi takdirde null bir degeri remove etmeye
				// calisir.
				Heap.set(0, Heap.get(--elemanSayi));
				heapify(0);
			}
		}

		public void heapify(int i) {
			try {
				if (!isLeaf(i)) {
					// Leaf degilse heapify cagirilir aksi takdirde bu fonksiyona erisilememelidir.
					if (Heap.get(i) != null && Heap.get(leftChild(i)) != null && Heap.get(rightChild(i)) != null) {
						// Null point kontrolu yapilmistir fakat yine de ne olur ne olmaz try catch
						// icinde cagirildi.
						if (Heap.get(i) > Heap.get(leftChild(i)) || Heap.get(i) > Heap.get(rightChild(i))) {
							// Ust basamaktaki deger sol cocuktan buyuk ya da sagdaki cocuktan kucuk oldugu
							// takdirde asagidaki swap kisimlarina girmeye baslar.
							if (Heap.get(leftChild(i)) < Heap.get(rightChild(i))) {
								swap(i, leftChild(i));
								heapify(leftChild(i));
							} else {
								swap(i, rightChild(i));
								heapify(rightChild(i));
							}
						}
					}
				}
			} catch (Exception e) {
			}

		}

		public void swap(int x, int y) {
			// Minheap'te kullanilacak swap metodu buradadir, basitce index degerlerini
			// arraylist'te ustune set ederek degisim yapar.

			int swaplenen = Heap.get(x); // Degerin kaybolmamasi icin bu int degeri pointer gorevi gorur.
			Heap.set(x, Heap.get(y));
			Heap.set(y, swaplenen);
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			if (elemanSayi == 1) {
				// Burada eger child yoksa sadece root'u basar,
				// child'lari da var ise else kismina girer
				sb.append(Heap.get(0));
				sb.append("\n");
			} else {
				for (int i = 0; i < (elemanSayi / 2); i++) {
					if (Heap.get(i) != 0 || Heap.get(i) != null) // Burada external
						sb.append("\t" + Heap.get(i) + "\n");
					if (leftChild(i) < elemanSayi && (Heap.get(leftChild(i)) != 0 || Heap.get(leftChild(i)) != null)) {
						// Sol cocuk 0 veya null'a esit degilse basar.
						sb.append(Heap.get(leftChild(i)));
						// Left child sola dayali basilir

					}
					if (rightChild(i) < elemanSayi
							&& (Heap.get(rightChild(i)) != 0 || Heap.get(rightChild(i)) != null)) {
						// Sag cocuk 0 veya null'a esit degilse basar.
						sb.append("\t\t" + Heap.get(rightChild(i)));
						// Right child onune 2 tab gelir iki kardesin ayrilmasi icin

					}
					sb.append("\n");
				}
			}
			return sb.toString();
		}

	}

	public MinHeap schedulerTree = new MinHeap();
	public Integer timer = 0;
	public String filePath;
	public HashMap<Integer, ArrayList<Integer>> dependencyMap = new HashMap<>();
	// DependencyMap integer ve bir arraylist olarak tutulmasina karar verilmistir
	// cunku gelen job'larda birden fazla dependency olabilir, aksi takdirde sadece
	// bir tanesi tutulabilirdi.
	public ArrayList<Resource> resourceList = new ArrayList<>();
	public ArrayList<Job> waitList = new ArrayList<>();
	public StringBuilder allTimeLinePrinter = new StringBuilder();
	public ArrayList<Job> completedJobs = new ArrayList<>();
	public Scanner in = null;

	public JobScheduler(String filePath) {
		this.filePath = filePath;
		try {
			in = new Scanner(new FileInputStream(filePath));
			// Scanner burada acilir cunku stillContinues kullanilmasi icin gereklidir.
		} catch (Exception e) {
		}

	}

	public void insertDependencies(String dependencyPath) {
		try {
			Scanner dependencies = new Scanner(new FileInputStream(dependencyPath));
			while (dependencies.hasNextLine()) {
				String tmp = "";
				tmp = dependencies.nextLine();
				if (!dependencyMap.containsKey(Integer.parseInt(tmp.charAt(0) + ""))) {

					// dependencyMap'e bu job daha once gelmemis demektir bu yuzden bu key'e sahip
					// degildir, bu takdirde yeni bir arraylist olusturulur ve bu key'in value'si
					// olarak set edilir.

					ArrayList<Integer> tempList = new ArrayList<Integer>();
					tempList.add(Integer.parseInt(tmp.charAt(2) + ""));
					dependencyMap.put(Integer.parseInt(tmp.charAt(0) + ""), tempList);
				} else {

					// Eger dependencyMap'e bu job daha once geldiyse key olarak tutulmustur
					// demektir, bu takdirde o key'in bagli oldugu arraylist'e ekleme yapilmalidir.
					// Bu if kosulu bunu yapmaya yarar.

					ArrayList<Integer> temp = dependencyMap.get(Integer.parseInt(tmp.charAt(0) + ""));
					temp.add(Integer.parseInt(tmp.charAt(2) + ""));
					dependencyMap.put(Integer.parseInt(tmp.charAt(0) + ""), temp);
				}

			}
		} catch (Exception e) {
		}

	}

	public boolean stillContinues() {

		// Girilen path'te daha fazla okuma islemi olup olmayacagini kontrol eder.

		if (in.hasNextLine())
			return true;
		else
			return false;
	}

	public void run() {
		addingJobs(); // job'lari ekleyen metod.
		addString(); // all time printer'a asama asama eklenen string.
	}

	public void setResourcesCount(Integer count) {

		// Burasi verilen sayiya gore yeni resource olusturulur.

		for (int i = 1; i < count + 1; i++) {
			resourceList.add(new Resource(i));
		}

	}

	public void insertJob() {
		String tmp = "";
		try {
			if (in.hasNextLine()) {
				tmp = in.nextLine();
			}

		} catch (Exception e) {
		}

		if ((!tmp.equals("no job")) && (!tmp.equals(""))) {

			// No job veya bos satir olmamasi durumunda ilk index job id'sine 2.index ise
			// durationTime'a aittir, burada bu olusturulan job waitList'e ve minHeap'e
			// atilir.

			int boslukIndex = tmp.indexOf(" "); // Line'daki bosluk index'i
			int sayi = Integer.parseInt(tmp.substring(0, boslukIndex)); // Id burada alinir.
			int duration = Integer.parseInt(tmp.substring(boslukIndex + 1)); // Duration time burada alinir.
			schedulerTree.insert(Integer.parseInt(tmp.charAt(0) + ""));
			waitList.add(new Job(timer, sayi, duration));

		}
		timer++; // timer artirilir.
	}

	public void completedJobs() {

		// Kill job yapilip completed jobs'a eklenen job'lar burada tutulur.
		StringBuilder completedJobsString = new StringBuilder();

		completedJobsString.append("completed jobs ");
		for (int a = 0; a < completedJobs.size(); a++)
			completedJobsString.append(completedJobs.get(a).id + ", ");

		completedJobsString.deleteCharAt(completedJobsString.length() - 1);
		completedJobsString.deleteCharAt(completedJobsString.length() - 1);

		// Bu delete islemlerinin yapilma amaci completed job'lar bittikten sonra virgul
		// eklenmesinin onune gecmektir. Son index'te boslugu ve ondan onceki index'te
		// de virgulu siler.

		completedJobsString.append("\n");

		System.out.print(completedJobsString.toString());
	}

	public void dependencyBlockedJobs() {

		// Burada waitList'te bekleyen job'lar kontrol edilir ve bekleyen job'un
		// dependency'si varsa dependency blocked olmus demektir. Hashmap kullanilarak
		// bulunan dependency listesinden dependent oldugu job, get metodu ile cekilir.

		int i = 0;
		int j = 0;
		// i ve j variable'lari asagida print edilirken erisebilmek icin burada
		// tanimlanmistir.

		System.out.print("dependency blocked jobs ");
		for (i = 0; i < waitList.size(); i++) {
			boolean bulduMu = false;
			if (hasDependency(waitList.get(i).id)) {
				System.out.print("(" + waitList.get(i).id + ",");
				for (j = 0; j < dependencyMap.get(waitList.get(i).id).size(); j++) {
					if (completedJobs.size() < 1) {
						System.out.print(dependencyMap.get(waitList.get(i).id).get(j) + ") ");
						break;
					}
					for (int k = 0; k < completedJobs.size(); k++) {
						if (!(dependencyMap.get(waitList.get(i).id).get(j) == completedJobs.get(k).id)) {
							// Eger dependency'si olan job birden fazla job'a dependent ise
							// completedJob'larda gezilir ve hangisinin calismaya engel oldugu burada
							// belirlenir.
							bulduMu = true;
							break;
						}
					}
				}
				if (bulduMu) {
					// Dependent olan job completed job'lara girmemistir ve hala dependent demektir,
					// buraya girer ve basma islemi gerceklesir.
					System.out.print(dependencyMap.get(waitList.get(i).id).get(j - 1) + ") ");
				}

			}
		}

		System.out.println();
	}

	public void resourceBlockedJobs() {

		// Buradaki job'lar waitList'te kalmis fakat dependency blocked olmayanlardir,
		// yani kaynaklar doludur ve resource blocked olmuslardir.

		System.out.print("resource blocked jobs ");
		for (int i = 0; i < waitList.size(); i++) {
			if (!hasDependency(waitList.get(i).id))
				System.out.print(waitList.get(i).id + " ");
		}
		System.out.println();
	}

	public void workingJobs() {

		// Burada calisan job'lar ve onlarin ait oldugu kaynak id'si bastirilir.

		System.out.print("working jobs ");
		for (int i = 0; i < resourceList.size(); i++) {
			if (!resourceList.get(i).isEmpty())
				System.out.print("(" + resourceList.get(i).jobs.get(0).id + "," + resourceList.get(i).id + ") ");

		}
		System.out.println();
		killJobIfNecessary();
		// Calisan job'lar bitti mi diye kontrol etmek icin bu metod yazilmistir,
		// bittiyse eger completedJobs'a giderler.

	}

	public void killJobIfNecessary() {

		// Burada tum resource'lardaki start metodu calisir, bu da durationTime'larini 1
		// azaltmak demektir.
		// Duration time kalmadiysa yani job bitti ise burada killJob yapilir ve
		// completedJobs'a eklenir.

		for (int i = 0; i < resourceList.size(); i++) {
			resourceList.get(i).start();
			if (resourceList.get(i).durationTime == 0) {
				if (!resourceList.get(i).isEmpty()) {
					completedJobs.add(resourceList.get(i).jobs.get(0));
					resourceList.get(i).endedTimes.add(timer);
					resourceList.get(i).killJob();
				}
			}
		}
	}

	public boolean hasNotFinished() {

		// Burada tum resource'lar kontrol edilir ve iclerinden en az biri bile bos
		// degilse true return edilir.

		for (int i = 0; i < resourceList.size(); i++) {
			if (!resourceList.get(i).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public void runAllRemaining() {
		// Timer artirilip bir kez run edilir ve digerleri dongu icinde kontrol edilir.
		timer++;
		run();

		while (hasNotFinished() || waitList.size() > 0) {
			// Ustte verilen kosulda ya resource'lar hala calisiyordur ya da hala
			// waitList'te bekleyen job'lar vardir. Bu kosullar devam ettigi surece run ve
			// killJobs metodu calismaya devam eder.
			timer++;
			killJobIfNecessary();
			run();
		}

	}

	public void addString() {

		// Bu metod allTimeLine print edilmesi icin arkaplanda stringbuilder'e ekleme
		// yapar.
		if (hasNotFinished() || waitList.size() > 0) {
			// Ustte verilen kosulda ya resource'lar hala calisiyordur ya da hala
			// waitList'te bekleyen job'lar vardir. Bu yuzden timer printer hala
			// calismalidir.
			allTimeLinePrinter.append(timer);
		}

		for (int i = 0; i < resourceList.size(); i++) {
			if (resourceList.get(i).isEmpty()) {
				// Eger kaynak bos ise tab eklenmelidir.
				allTimeLinePrinter.append("\t");
			}
			if (!resourceList.get(i).isEmpty()) {
				// Kaynak bos olmadigi takdirde kaynaktaki ilgili job'un id'si eklenir.
				allTimeLinePrinter.append("\t" + resourceList.get(i).jobs.get(0).id);
			}
		}
		allTimeLinePrinter.append("\n");
	}

	public void allTimeLine() {

		// Still continues metodu false dondugunde buraya girer ve allTime'da neler
		// yapildigi basilir.

		System.out.println();
		System.out.println("All Time Line");
		System.out.print("Zaman");
		for (int i = 0; i < resourceList.size(); i++)
			System.out.print("\t" + resourceList.get(i));

		System.out.println();

		System.out.println(allTimeLinePrinter.toString());

	}

	public String toString() {

		// MinHeap'in basilmasi icin gerekli toString metodu MinHeap'in kendi icindeki
		// toString'i cagirir.

		return schedulerTree.toString();
	}

	public boolean hasDependency(Integer id) {

		// Burada gelen id'den hashMap key'lerinden kontrol yapilir. Eger key dahilse
		// dependency var demektir.

		if (dependencyMap.containsKey(id))
			return true;
		else
			return false;
	}

	public boolean isDependencyOver(Integer id) {

		// Burada gelen id'nin hashMap'te karsilik geldigi dependency'lerinin oldugu
		// array'e erisilir.

		// Eger erisilen array'deki elemanlar completedJobs'da yani tamamlanan
		// islerdeyse bagimlilik bitti demektir ve gelen job kaynaklara atilabilir.

		for (int i = 0; i < completedJobs.size(); i++) {
			for (int j = 0; j < dependencyMap.get(id).size(); j++)
				if (completedJobs.get(i).id == dependencyMap.get(id).get(j)) {
					return true;
				}
		}
		return false;

	}

	public void addingJobs() {
		for (int i = 0; i < resourceList.size(); i++) {
			if (resourceList.get(i).isEmpty()) {
				for (int j = 0; j < waitList.size(); j++) {
					if (hasDependency(waitList.get(j).id)) {
						// Gelen job'in dependency'si oldugu zaman buraya girer.
						if (isDependencyOver(waitList.get(j).id)) {
							// Eger gelen job'in dependency'si bittiyse ekleme yapma kontrolune gecilir.
							if (!waitList.isEmpty() && resourceList.get(i).isEmpty()
									&& waitList.get(j).arrivalTime <= timer) {
								if (waitList.get(j).arrivalTime <= timer) {
									schedulerTree.remove(); // Eger job kaynaga atildiysa minHeap'ten remove edilir.
									resourceList.get(i).add(waitList.get(j));
									resourceList.get(i).arrivalTimes.add(waitList.get(j).arrivalTime);
									waitList.remove(j);
									resourceList.get(i).startedTimes.add(timer);
								}
							}
						}

					} else {
						// Dependency olmadigi durumda sadece kaynak kontrolu yapilir ve eklenir.
						if (!waitList.isEmpty() && resourceList.get(i).isEmpty()
								&& waitList.get(j).arrivalTime <= timer) {
							if (waitList.get(j).arrivalTime <= timer) {
								schedulerTree.remove(); // Eger job kaynaga atildiysa minHeap'ten remove edilir.
								resourceList.get(i).add(waitList.get(j));
								resourceList.get(i).arrivalTimes.add(waitList.get(j).arrivalTime);
								waitList.remove(j);
								resourceList.get(i).startedTimes.add(timer);
							}
						}

					}
				}

			}
		}

	}
}

class Job {
	int arrivalTime;
	int id;
	int duration;

	public Job(int arrivalTime, int id, int duration) {
		this.arrivalTime = arrivalTime;
		this.id = id;
		this.duration = duration;
	}
}

class Resource {
	Integer id;
	Integer durationTime;
	Integer totalTime;

	ArrayList<Job> jobs = new ArrayList<>();
	// Yapilmak uzere gelen job'u tutar bittigi zaman silinir.
	ArrayList<Job> oldJobs = new ArrayList<>();
	// Bitenler dahil tum yapilan job'lari tutar.
	ArrayList<Job> endedJobs = new ArrayList<>();
	// Biten job'lari tutar.
	ArrayList<Integer> arrivalTimes = new ArrayList<>();
	// Resource'a eklenen job'larin arrivalTime'larini sirayla tutar.
	ArrayList<Integer> startedTimes = new ArrayList<>();
	// Resource'a eklenen job'larin baslangic surelerini sirayla tutar.
	ArrayList<Integer> endedTimes = new ArrayList<>();
	// Resource'a eklenen job'larin bitis surelerini sirayla tutar.

	public Resource(int i) {
		this.id = i;
		this.durationTime = 0;
		this.totalTime = 0;
	}

	public String toString() {
		return "R" + this.id;
	}

	public void add(Job j) {
		if (durationTime == 0) {
			oldJobs.add(j);
			// Verinin kaybolmamasi icin ayni zamanda history olarak tutulan oldJobs'a da
			// eklenir.
			jobs.add(j);
		}
		this.durationTime += j.duration;
		this.totalTime += j.duration;
	}

	public void start() {
		// Burasi her bir zaman araliginda job'larin durationTime'ini azaltir.
		if (durationTime > 0) {
			durationTime--;
		}
	}

	public boolean isEmpty() {
		return (jobs.size() == 0);
	}

	public void killJob() {
		// durationTime bittiginde buraya girer ve job'u remove eder.
		endedJobs.add(jobs.get(0));
		jobs.remove(0);
	}

}
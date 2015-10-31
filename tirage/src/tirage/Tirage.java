package tirage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Tirage {
	private ArrayList<Integer> donneur = new ArrayList<Integer>();
	private ArrayList<Integer> receveur = new ArrayList<Integer>();
	private Map<Integer, String> mapNoms = new HashMap<Integer, String>();
	private Map<Integer, String> mapMails = new HashMap<Integer, String>();
	private List<HashMap<Integer, Integer>> listCouplesCadeaux = new ArrayList<HashMap<Integer, Integer>>();

	public void tire() {
		initialize();

		tirageAuSort();

		for (HashMap<Integer, Integer> couples : listCouplesCadeaux) {
			for (Integer idDonneur : couples.keySet()) {
				Integer idReceveur = couples.get(idDonneur);
				String donneur = mapNoms.get(idDonneur);
				String receveur = mapNoms.get(idReceveur);
				String email = mapMails.get(idDonneur);

				StringBuffer sb = new StringBuffer();
				sb.append("Hello " + donneur + " !\n\n");
				sb.append(
						"Un tirage au sort a été fait et ce mail est envoyé automatiquement à votre adresse afin que seuls vous sachiez qui est le destinataire de votre cadeau ! :)\n\n");
				sb.append(
						"Donc pour le Noël of the Tonneaux 2015, le couple à qui vous devrez amener un petit cadeau est :\n\n");
				sb.append("                              ----------------> "
						+ receveur + " <----------------\n\n");
				sb.append(
						"Petit rappel, le montant maximum du cadeau est de 30.- et les cadeaux pour les enfants ne sont pas comptés dedans :) \n\n");
				sb.append("A très vite ! Bisous bisous !!!!!!!!!!!!!");

				sendMailSSL(email, sb);
			}
		}
	}

	private void tirageAuSort() {
		Integer idDonneur = null;
		Integer idReceveur = null;

		while (!donneur.isEmpty()) {
			do {
				idDonneur = null;
				Integer iDon = randInt(1, 6);

				if (donneur.contains(iDon)) {
					idDonneur = iDon;
					donneur.remove(iDon);
				}
			} while (idDonneur == null);

			do {
				idReceveur = null;
				Integer iRec = randInt(1, 6);

				if (iRec != idDonneur && receveur.contains(iRec)) {
					idReceveur = iRec;
					receveur.remove(iRec);
				}
			} while (idReceveur == null);

			HashMap<Integer, Integer> mapCouple = new HashMap<Integer, Integer>();
			mapCouple.put(idDonneur, idReceveur);
			listCouplesCadeaux.add(mapCouple);
		}
	}

	private void initialize() {
		donneur.add(1);
		donneur.add(2);
		donneur.add(3);
		donneur.add(4);
		donneur.add(5);
		donneur.add(6);

		receveur.add(1);
		receveur.add(2);
		receveur.add(3);
		receveur.add(4);
		receveur.add(5);
		receveur.add(6);

		mapNoms.put(1, "Laetitia et Cédric");
		mapNoms.put(2, "Cri et Nico");
		mapNoms.put(3, "Dama et Coco");
		mapNoms.put(4, "Les Flows");
		mapNoms.put(5, "Aline et Pascal");
		mapNoms.put(6, "Franzi et Yannick");

		mapMails.put(1, "cebullo@gmail.com");
		mapMails.put(2, "cebullo@gmail.com");
		mapMails.put(3, "cebullo@gmail.com");
		mapMails.put(4, "cebullo@gmail.com");
		mapMails.put(5, "cebullo@gmail.com");
		mapMails.put(6, "cebullo@gmail.com");
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	private void sendMail(String email, StringBuffer text) {
		final String username = "cebullo@gmail.com";
		final String password = "";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("cedric.bulloni@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setSubject("Nowel des Tonneaux 2015");
			message.setText(text.toString());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private void sendMailSSL(String email, StringBuffer text) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("cebullo@gmail.com",
								"");
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("cebullo@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setSubject("Nowel des Tonneaux 2015 !!!");
			message.setText(text.toString());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}

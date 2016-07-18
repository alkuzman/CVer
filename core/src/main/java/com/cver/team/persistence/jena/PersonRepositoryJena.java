package com.cver.team.persistence.jena;

import com.cver.team.model.Person;
import com.cver.team.model.Provider;
import com.cver.team.model.Role;
import com.cver.team.model.literal.Identifier;
import com.cver.team.persistence.PersonRepository;
import com.cver.team.persistence.jena.helper.JenaPreferences;
import com.cver.team.persistence.jena.helper.ResourcePrefixes;
import com.cver.team.persistence.jena.helper.SPARQLPrefix;
import org.apache.jena.query.*;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public class PersonRepositoryJena implements PersonRepository {

    @Override
    public Person savePerson(Person person) {
        String email = person.getEmail();
        String password = person.getPassword();
        String id = person.getIdentifier().getId();
        String provider = person.getProvider().toString();
        String role = person.getRole().toString();
        String firstName = person.getFirstName();
        String lastName = person.getLastName();

        String loginEmailID = UUID.randomUUID().toString();
        String firstNameID = UUID.randomUUID().toString();
        String lastNameID = UUID.randomUUID().toString();



        ParameterizedSparqlString queryString = new ParameterizedSparqlString();

        queryString.setNsPrefix("cver", SPARQLPrefix.cvr);
        queryString.setNsPrefix("cvo", SPARQLPrefix.cvo);

        System.out.println("PASSWORD IS : "+password);

        queryString.setCommandText("INSERT {" +
                " ?userUri cvo:loginEmail ?email; " +
                " cvo:password ?passwordValue; " +
                " cvo:userID ?id ; " +
                " cvo:provider ?provider ; " +
                " cvo:role ?role ; " +
                " cvo:defaultFirstName ?firstName ; " +
                " cvo:firstName ?firstName ; " +
                " cvo:defaultLastName ?lastName ;" +
                " cvo:lastName ?lastName ;  " +
                " a cvo:Person . " +

                " ?email cvo:mbox ?emailValue; " +
                " a cvo:Email . " +

                " ?firstName cvo:value ?firstNameValue; " +
                " a cvo:LiteralWrapper. " +
                " ?lastName cvo:value ?lastNameValue; " +
                " a cvo:LiteralWrapper. "+


                "} WHERE { }");


        queryString.setIri("userUri", ResourcePrefixes.USER_PREFIX + id);

        queryString.setIri("email", ResourcePrefixes.EMAIL_PREFIX + loginEmailID);

        if(password != null)
        queryString.setLiteral("passwordValue", password);

        queryString.setLiteral("id", id);

        queryString.setLiteral("emailValue", email);

        queryString.setLiteral("provider", provider);

        queryString.setLiteral("role", role);

        queryString.setIri("firstName", ResourcePrefixes.LITERAL_WRAPPER + firstNameID);

        if(firstName != null)
        queryString.setLiteral("firstNameValue", firstName);


        queryString.setIri("lastName", ResourcePrefixes.LITERAL_WRAPPER + lastNameID);

        if(lastName != null)
        queryString.setLiteral("lastNameValue", lastName);

        System.out.println(queryString.toString());
        UpdateRequest updateRequest = UpdateFactory.create(queryString.toString());
        UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, JenaPreferences.UpdateEndpoint);
        updateProcessor.execute();
        System.out.println("UPDATE WAS SUCCESFULL");

        Identifier identifier = new Identifier();
        identifier.setURI(ResourcePrefixes.USER_PREFIX + id);
        identifier.setId(id);
        person.setIdentifier(identifier);
        return person;
    }

    @Override
    public boolean isEmailTaken(String email) {
        ParameterizedSparqlString queryString = new ParameterizedSparqlString();
        queryString.setNsPrefix("cvo", SPARQLPrefix.cvo);
        queryString.setCommandText("ASK {"+
                "?user cvo:loginEmail ?email .\n" +
                " ?email cvo:mbox ?emailValue . }");
        queryString.setLiteral("emailValue", email);
        System.out.println(queryString.toString());
        Query query = QueryFactory.create(queryString.toString());

        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(JenaPreferences.SPARQLEndpoint, query);
        return queryExecution.execAsk();
    }

    @Override
    public Person getPersonByLoginEmail(String email) {
        ParameterizedSparqlString queryString = new ParameterizedSparqlString();
        queryString.setNsPrefix("cvo", SPARQLPrefix.cvo);
        queryString.setNsPrefix("cver", SPARQLPrefix.cvr);

        queryString.setCommandText("SELECT  ?userID ?userPassword ?provider ?role ?firstName ?lastName \n" +
                " WHERE { " +
                " ?user cvo:userID ?userID ; \n"+
                " cvo:loginEmail ?email ;  \n" +
                " cvo:role ?role ; \n " +
                " cvo:defaultFirstName ?oFirstName ; \n " +
                " cvo:defaultLastName ?oLastName ; \n " +
                " cvo:provider ?provider . \n " +
                " ?email cvo:mbox ?userEmail . \n " +
                " ?oFirstName cvo:value ?firstName . \n " +
                " ?oLastName cvo:value ?lastName . " +
                " OPTIONAL  { ?user cvo:password ?userPassword . } \n "+

                "}");

        queryString.setLiteral("userEmail", email);

        System.out.println(queryString.toString());
        Query query = QueryFactory.create(queryString.toString());
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(JenaPreferences.SPARQLEndpoint, query);
        ResultSet set = queryExecution.execSelect();
        Person person;

        if(set.hasNext()) {

            System.out.println(" I GOT A RESULT FROM SELECT !!");

            person = new Person();

            QuerySolution currentEntry = set.next();

            Identifier identifier = new Identifier();
            identifier.setId(currentEntry.get("userID").toString());
            identifier.setURI(ResourcePrefixes.USER_PREFIX + identifier.getId());
            person.setIdentifier(identifier);

            person.setEmail(email);

            String role = currentEntry.get("role").toString();

            String provider = currentEntry.get("provider").toString();

            if(person.getProvider() == Provider.LOCAL)
            person.setPassword(currentEntry.get("userPassword").toString());

            person.setRole(Role.valueOf(role));

            person.setProvider(Provider.valueOf(provider));

            person.setFirstName(currentEntry.get("firstName").toString());

            person.setLastName(currentEntry.get("lastName").toString());

            return person;
        }
        else return null;

    }

    @Override
    public Person deletePerson(Person person) {
        return null;
    }
}

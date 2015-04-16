/*
 * GenericRepositoryBean.java 
 */
package edu.hm.cs.fwp.jeetrain.framework.core.persistence;

import java.util.List;
import java.util.NoSuchElementException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Generisches {@code Repository} als dünne Schicht über einem {@link EntityManager}, um gemeinsame
 * JPA-Zugriffe immer auf die gleiche Art und Weise zu lösen.
 * 
 * @author theism
 * @version 1.0
 * @since 24.03.2015
 */
@Stateless
public class GenericRepositoryBean {
    /**
     * Entitäten-Manager für die Verwaltung persistenter Entitäten.
     */
    @PersistenceContext
    EntityManager em;
    /**
     * Fügt die angegebene Entität in den Datastore ein und synchronisiert diese mit datastore-seitig
     * generierten beziehungsweise veränderten Feldern.
     * 
     * @param entity hinzuzufügende Entität (erforderlich)
     */
    public void addEntity(Object entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
    }
    /**
     * Liefert die Entität vom angebenen Typ mit dem angebenen eindeutigen technischen Bezeichner 
     * zurück.
     * 
     * @param entityType erwartete Entitätentyp
     * @param entityId eindeutiger technischer Bezeichner
     * @return gefundene Entität, falls eine Entität mit dem angebenen Bezeichner exisitert; sonst {@code null}
     */
    public <T> T getEntityById(Class<T> entityType, Object entityId) {
        return em.find(entityType, entityId);
    }
    /**
     * Liefert die Entität vom angebenen Typ mit dem angebenen eindeutigen technischen Bezeichner 
     * zurück, wobei erwartet wird, dass eine Entität mit dem angegeben Beeichner existiert.
     * 
     * @param entityType erwartete Entitätentyp
     * @param entityId eindeutiger technischer Bezeichner
     * @return gefundene Entität, falls eine Entität mit dem angebenen Bezeichner exisitert; niemals {@code null}
     * @throws NoSuchElementException - falls keine Entität mit dem angegebenen Bezeichner gefunden werden kann.
     */
    public <T> T getRequiredEntityById(Class<T> entityType, Object entityId) {
        T result = getEntityById(entityType, entityId);
        if (result == null) {
            throw new NoSuchElementException("Missing required entity of type ["
                + entityType.getName() + "] with unique identifier [" + entityId + "]!");
        }
        return result;
    }
    /**
     * Aktualisiert den Zustand der Entität im Datastore mit der angegebenen Entität.
     * <p>
     * Die angegebene Entität muss zuvor mit {{@link #addEntity(Object)} dem Datastore hinzugefügt worden sein.
     * </p>
     * @param entity zu aktualisierende Entität
     */
    public void setEntity(Object entity) {
        em.merge(entity);
    }
    /**
     * Entfernt die angegebene Entität aus dem Datastore.
     * <p>
     * Die angegebene Entität muss zuvor mit {{@link #addEntity(Object)} dem Datastore hinzugefügt worden sein.
     * </p>
     * @param entity zu löschende Entität
     */
    public void removeEntity(Object entity) {
        Object mergedEntity = em.merge(entity);
        em.remove(mergedEntity);
    }
    /**
     * Entfernt die Entität mit dem angegebenen eindeutigen Bezeichner aus dem Datastore.
     * <p>
     * Achtung: Beim Löschen über ID ist zu beachten, dass optimistische Sperren nicht geprüft werden können.
     * </p>
     * @param entityType erwarteter Entitätentyp
     * @param entityId eindeutiger technischer Bezeichner der zu löschende Entität
     */
    public <T> void removeEntityById(Class<T> entityType, Object entityId) {
        T entity = em.find(entityType, entityId);
        if (entity != null) {
            em.remove(entity);
        }
    }
    /**
     * Sucht eine einzelne Entität des angegebenen Typs mit der angegebenen 
     * Named Query und den angegebenen Query-Parametern und liefert das Ergebnis
     * der Query zurück.
     * @param entityType erwarteter Entitätentyp
     * @param queryName eindeutiger Name einer Named Query
     * @param queryParameters anzuwendende Query-Parameter (optional)
     * @return gefundene Entität, falls die Query ein Ergebnis geliefert hat; sonst {@code null}.
     */
    public <T> T queryEntity(Class<T> entityType, String queryName, QueryParameters queryParameters) {
        TypedQuery<T> query = em.createNamedQuery(queryName, entityType);
        if (queryParameters != null) {
            queryParameters.applyParameters(query);
        }
        T result = null;
        try {
        	result =  query.getSingleResult();
        } catch (NoResultException ex) {
        	// Es ist OK wenn nichts gefunden wird
        }
        return result;
    }
    /**
     * Sucht eine einzelne Entität des angegebenen Typs mit der angegebenen 
     * Named Query und den angegebenen Query-Parametern und liefert das Ergebnis
     * der Query zurück.
     * <p>
     * Im Gegensatz zu  {@link #queryEntity(Class, String, QueryParameters)} wird allerdings
     * davon ausgegangen, dass die gewünschte Entität existiert.
     * </p>
     * @param entityType erwarteter Entitätentyp
     * @param queryName eindeutiger Name einer Named Query
     * @param queryParameters anzuwendende Query-Parameter (optional)
     * @return gefundene Entität, niemals {@code null}.
     * @throws NoSuchElementException - falls keine Entität gefunden werden kann
     */
    public <T> T queryRequiredEntity(Class<T> entityType,
        String queryName,
        QueryParameters queryParameters) {
        T result = queryEntity(entityType, queryName, queryParameters);
        if (result == null) {
            throw new NoSuchElementException("Expected query [" + queryName
                + "] with query parameters [" + queryParameters
                + "] to find exactly one entity of type [" + entityType
                + "] but actually found none!");
        }
        return result;
    }
    /**
     * Sucht eine Menge von Entität des angegebenen Typs mit der angegebenen 
     * Named Query und den angegebenen Query-Parametern und liefert das Ergebnis
     * der Query zurück.
     * @param entityType erwarteter Entitätentyp
     * @param queryName eindeutiger Name einer Named Query
     * @param queryParameters anzuwendende Query-Parameter (optional)
     * @return Liste der gefundenen Entitäten, niemals {@code null}
     */
    public <T> List<T> queryEntities(Class<T> entityType,
        String queryName,
        QueryParameters queryParameters) {
        TypedQuery<T> query = em.createNamedQuery(queryName, entityType);
        if (queryParameters != null) {
            queryParameters.applyParameters(query);
        }
        return query.getResultList();
    }
    /**
     * Ermittelt die Anzahl der Entitäten des angegebenen Typs mit der angegebenen 
     * Named Query und den angegebenen Query-Parametern und liefert das Ergebnis
     * der Query zurück.
     * @param queryName eindeutiger Name einer Named Query
     * @param queryParameters anzuwendende Query-Parameter (optional)
     * @return Anzahl der gefundenen Entitäten, die zu den Query-Parametern passen
     */
    public long countEntities(String queryName, QueryParameters queryParameters) {
        TypedQuery<Long> query = em.createNamedQuery(queryName, Long.class);
        if (queryParameters != null) {
            queryParameters.applyParameters(query);
        }
        return query.getSingleResult();
    }
    /**
     * Sucht eine Menge von Entität des angegebenen Typs mit der angegebenen 
     * Named Query und den angegebenen Query-Parametern und liefert das Ergebnis
     * der Query zurück.
     * @param entityType erwarteter Entitätentyp
     * @param queryName eindeutiger Name einer Named Query
     * @param queryParameters anzuwendende Query-Parameter (optional)
     * @param startIndex Start-Index des ersten Treffers in der Ergebnismenge
     * @param pageSize maximal Anzahl an Treffern pro Seite
     * @return Liste der gefundenen Entitäten, niemals {@code null}
     */
    public <T> List<T> queryEntitiesWithPagination(Class<T> entityType,
        String queryName,
        QueryParameters queryParameters, int startIndex, int pageSize) {
        TypedQuery<T> query = em.createNamedQuery(queryName, entityType);
        if (queryParameters != null) {
            queryParameters.applyParameters(query);
        }
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}

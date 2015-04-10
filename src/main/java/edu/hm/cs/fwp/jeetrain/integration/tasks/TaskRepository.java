/* TaskRepository.java 
 */
package edu.hm.cs.fwp.jeetrain.integration.tasks;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.framework.core.persistence.GenericRepository;

/**
 * {@code Repository} that manages {@code Task} entities.
 * 
 * @author theism
 * @since 1.0
 */
public interface TaskRepository extends GenericRepository<Long, Task> {

}

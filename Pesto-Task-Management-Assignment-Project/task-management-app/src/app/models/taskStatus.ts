export enum TaskStatus {
  TODO = 'TODO',
  IN_PROGRESS = 'IN_PROGRESS',
  DONE = 'DONE',
}

export function fromString(status: string): TaskStatus | null {
  if (!status) {
    return null; 
  }

  const uppercasedStatus = status.toUpperCase();

  if (Object.values(TaskStatus).includes(uppercasedStatus as TaskStatus)) {
    return uppercasedStatus as TaskStatus;
  }

  throw new Error(`No constant with the specified name: ${status}`);
}
SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM mailingLists;
DELETE FROM maintenanceEvents;
DELETE FROM mangoViews;
DELETE FROM mangoViewUsers;
DELETE FROM pointEventDetectors;
DELETE FROM pointHierarchy;
DELETE FROM pointLinks;
DELETE FROM pointValueAnnotations;
DELETE FROM compoundEventDetectors;
DELETE FROM dataPoints;
DELETE FROM dataPointUsers;
DELETE FROM dataSources;
DELETE FROM dataSourceUsers;
DELETE FROM eventHandlers;
DELETE FROM events;
DELETE FROM flexProjects;
DELETE FROM mailingListInactive;
DELETE FROM mailingListMembers;
DELETE FROM pointValues;
DELETE FROM publishers;
DELETE FROM reportInstanceData;
DELETE FROM reportInstanceDataAnnotations;
DELETE FROM reportInstanceEvents;
DELETE FROM reportInstancePoints;
DELETE FROM reportInstances;
DELETE FROM reportInstanceUserComments;
DELETE FROM reports;
DELETE FROM scheduledEvents;
DELETE FROM scripts;
DELETE FROM systemSettings;
DELETE FROM userComments;
DELETE FROM userEvents;
DELETE FROM users;
DELETE FROM watchListPoints;
DELETE FROM watchLists;
DELETE FROM watchListUsers;
DELETE FROM templatesDetectors;
DELETE FROM eventDetectorTemplates;
DELETE FROM usersProfiles;
DELETE FROM dataSourceUsersProfiles;
DELETE FROM dataPointUsersProfiles;
DELETE FROM usersUsersProfiles;
DELETE FROM viewUsersProfiles;
DELETE FROM watchListUsersProfiles;
SET FOREIGN_KEY_CHECKS = 1;
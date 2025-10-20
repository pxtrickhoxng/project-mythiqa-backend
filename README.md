# Mythiqa

Mythiqa is a full-stack MVP for a collaborative storytelling platform that empowers users to co-author, publish, and explore narrative content—primarily those focused on Fantasy and Science Fiction. The platform combines modern frontend technologies, scalable backend infrastructure, and several valuable writing tools to deliver an engaging and intuitive experience for writers and readers alike.

---

## Screenshots

The following images showcase some of Mythiqa’s key features. More will be added soon!

### Author Dashboard
<img width="1914" height="841" alt="image" src="https://github.com/user-attachments/assets/06b304a5-418a-4a48-b24d-1b591b48d601" />

### Book Overview
<img width="991" height="831" alt="image" src="https://github.com/user-attachments/assets/56fe4990-747b-4a52-9973-f5cbc37e0880" />

### Story Creation Form
<img width="743" height="850" alt="image" src="https://github.com/user-attachments/assets/2f2394e3-a8f1-4982-afeb-602f593aff6e" />

### Rich Text Editor for writing Chapters
<img width="1266" height="677" alt="image" src="https://github.com/user-attachments/assets/03dc66f2-f0b3-4692-9a6a-ffee7e2ec7af" />

### Reading Experience
<img width="1525" height="681" alt="image" src="https://github.com/user-attachments/assets/3552599c-b131-475a-965f-bb82c18ce2fe" />

### Timeline Notes
<img width="1645" height="818" alt="image" src="https://github.com/user-attachments/assets/9678a6dc-f090-498b-8513-e20eb8fee7c3" />

## Tech Stack

- **Frontend:** Next.js, Tailwind CSS, TypeScript  
- **Backend:** Springboot (Java)  
- **Cloud & Data Storage:** AWS (RDS/PostgreSQL, S3)  
- **AI Integration:** Anthropic API for AI-assisted writing tools

---

## Key Features

- **Collaborative Storytelling**  
  Enables multiple users to co-author stories with built-in tools such as rich-text notes, narrative timelines, and character creation blueprints.

- **AI-Assisted Writing Tools**  
  Powered by the Claude Anthropic LLM, writers can access intelligent features for plot development, worldbuilding, and character ideation. Please refer to the bottom of the page for further explanation regarding AI usage in creative writing.

- **Responsive Frontend**  
  Built with Next.js and Tailwind CSS for a sleek, modern, and responsive user experience.

- **Hybrid Data Architecture**  
  Combines PostgreSQL/RDS for structured metadata and S3 for storing user-uploaded media.

---

## How It Works

1. Users register and authenticate using Clerk to begin writing.  
2. The frontend communicates with Springboot backend endpoints for managing user and story data.  
3. Structured data (e.g., user accounts, story metadata, permissions), as well as chapter content and user story notes, are stored in PostgreSQL.   
4. Media assets such as profile pictures and book covers are stored in AWS S3 and retrieved via urls from the Postgres database.
5. Users are provided with several built-in story planning solutions, tackling common problems in creative writing such as plot ideation, character creation, and worldbuilding. Authors may choose to leverage AI to aid with ideas and brainstorming.  

---

## What I Learned

- Designing hybrid cloud storage solutions combining relational and object-storage databases for scalable full-stack applications.  
- Building modern server-rendered apps with Next.js and Tailwind CSS for optimal UX and performance.  
- Developing RESTful APIs using Springboot and integrating complex cloud services (AWS RDS, S3).
- Combining Clerk's built in authentication and authorization features with Spring Security to ensure overall site security. 

---

## Notes

Mythiqa is currently an MVP under active and rapid development. Features, integrations, and designs may evolve as the platform matures. The application is not yet deployed.

To preserve the integrity of creative writing, AI is intentionally not integrated directly into the text editor. Its use is limited to plotting, planning, and other related tools. This design choice encourages writers to craft their own stories without over-reliance on AI-generated content. Of course, this isn’t a foolproof safeguard since many external AI tools remain widely accessible.

As of now, no AI features have been implemented into the application. However, they will be fully integrated before deployment.

---

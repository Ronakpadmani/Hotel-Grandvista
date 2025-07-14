# The Grandvista Hotel – Frontend

A modern hotel booking web application built with React. Users can browse rooms, book stays, and pay online. Admins can manage rooms, bookings, and view revenue. This frontend connects to a Spring Boot backend and uses Stripe for secure payments.

## Features
- User registration and login
- Browse and search available rooms
- Room details with booking functionality
- Online payment via Stripe
- View and manage personal bookings
- Admin dashboard for managing rooms and bookings
- Responsive design for desktop and mobile

## Tech Stack
- **React** (with React Router)
- **Axios** (API requests)
- **Stripe** (payment integration)
- **React Day Picker** (date selection)
- **Spring Boot** (backend, not included here)

## Getting Started

### Prerequisites
- Node.js (v16 or higher recommended)
- npm (v8 or higher)

### Installation
1. Clone the repository:
   ```bash
   git clone <your-repo-url>
   cd hotel-react-frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Configure environment variables if needed (e.g., API base URL, Stripe keys).

### Running the App
Start the development server:
```bash
npm start
```
Visit [http://localhost:3000](http://localhost:3000) in your browser.

### Building for Production
```bash
npm run build
```
The optimized build will be in the `build/` folder.

## Project Structure
```
src/
  component/
    auth/           # Login, Register pages
    admin/          # Admin dashboard & management
    booking_room/   # Room listing, details, booking
    common/         # Shared UI components (Navbar, Footer, etc.)
    home/           # Home page
    payment/        # Payment components
    profile/        # User profile pages
  service/          # API service and route guards
  App.js            # Main app and routes
  index.js          # Entry point
  index.css         # Global styles
```

## Available Scripts
- `npm start` – Run the app in development mode
- `npm test` – Launch the test runner
- `npm run build` – Build for production
- `npm run eject` – Eject configuration (not recommended)

## API & Backend
This frontend expects a compatible backend (Spring Boot) running and accessible. Update API endpoints in `src/service/ApiService.js` if needed.

## Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---
*Built with ❤️ for The Grandvista Hotel project.*
